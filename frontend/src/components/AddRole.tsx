import { useSession } from "next-auth/react";
import { useRouter } from "next/navigation";
import { useState, useEffect, SetStateAction } from "react";

const PrivilegesPage = () => {
  const [privileges, setPrivileges] = useState([]);
  const [newRoleName, setNewRoleName] = useState("");
  const [selectedPrivileges, setSelectedPrivileges] = useState([]);
  const { data: session, status } = useSession();

  const router = useRouter();

  useEffect(() => {
    const fetchPrivileges = async () => {
      try {
        const response = await fetch(
          process.env.NEXT_PUBLIC_BACKEND_URL + "/api/privileges/all",
          {
            method: "GET",
            headers: {
              "Content-Type": "application/json",
              Authorization: `Bearer ${session?.user.accessToken}`,
            },
          }
        );
        const data = await response.json();
        setPrivileges(data);
      } catch (error) {
        console.error("Error fetching privileges:", error);
      }
    };

    fetchPrivileges();
  }, [status]);

  const handleCheckboxChange = (privilege) => {
    const updatedPrivileges = selectedPrivileges.includes(privilege)
      ? selectedPrivileges.filter((p) => p !== privilege)
      : [...selectedPrivileges, privilege];
    setSelectedPrivileges(updatedPrivileges);
  };

  const removeAllChecked = () => {
    setSelectedPrivileges([]);
  };

  console.log(selectedPrivileges);

  const handleRoleNameChange = (e: {
    target: { value: SetStateAction<string> };
  }) => {
    setNewRoleName(e.target.value);
  };

  const handleSubmit = async (e: { preventDefault: () => void }) => {
    e.preventDefault();

    try {
      const response = await fetch(
        process.env.NEXT_PUBLIC_BACKEND_URL + "/api/roles/add",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${session?.user.accessToken}`,
          },
          body: JSON.stringify({
            roleName: newRoleName,
            privilegeIds: selectedPrivileges,
          }),
        }
      ).then((res) => res.json());

      console.log(response);
      if (response.ok) {
        setNewRoleName("");
        setSelectedPrivileges([]);
        removeAllChecked();
      }
      alert(response.message);
      router.push("/");
    } catch (error) {
      console.error("Error creating role:", error);
    }
  };

  return (
    <div style={{ maxWidth: "600px", margin: "auto", padding: "20px" }}>
      <h1>Create New Role</h1>
      <form
        style={{ display: "flex", flexDirection: "column" }}
        onSubmit={handleSubmit}
      >
        <div style={{ marginBottom: "16px" }}>
          <label htmlFor="roleName" style={{ marginBottom: "8px" }}>
            Role Name:
          </label>
          <input
            type="text"
            id="roleName"
            value={newRoleName}
            onChange={handleRoleNameChange}
            style={{ marginRight: "8px" }}
          />
        </div>
        <div style={{ marginBottom: "16px" }}>
          <h2>Privileges:</h2>
          {privileges.map((privilege) => (
            <div key={privilege.id} style={{ marginBottom: "8px" }}>
              <input
                type="checkbox"
                id={privilege.id}
                checked={selectedPrivileges.includes(privilege.id)}
                onChange={() => handleCheckboxChange(privilege.id)}
                style={{ marginRight: "8px" }}
              />
              <label htmlFor={privilege.id}>
                {privilege.name} - {privilege.description}
              </label>
            </div>
          ))}
        </div>
        <button
          type="submit"
          style={{
            padding: "8px 16px",
            backgroundColor: "#4caf50",
            color: "white",
            border: "none",
            cursor: "pointer",
          }}
        >
          Create Role
        </button>
      </form>
    </div>
  );
};

export default PrivilegesPage;
