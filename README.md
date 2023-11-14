# Sentry Management Web App IITGN

## Setup Instructions

### 1. Download Docker

This project uses Docker Desktop for containerization.
Therefore is need to install the docker-desktop from the Docker website.

### 2. Create a `.env.local` file in the Frontend Folder of the project and include the following lines of code in it.

```env
NEXT_PUBLIC_BACKEND_URL="http://localhost:80/api"
BACKEND_URL="http://backend:8080"
JWT_SECRET="secret"
NEXTAUTH_SECRET="secret"
```

### 3. Running the Project

-   Now that you have Docker installed and the .env.local file configured, you can run the project:

-   Open a terminal in the project root directory.
    Run the following command to build and start the Docker containers:

```console
docker-compose up
```

-   The first time you run this command,`` Docker downloads the images for the containers from Docker Hub.
-   Once the download is complete, the containers are built and started.

### 4. url

`backend_url`
[backend_url](http://localhost:8080/swagger-ui/index.html#/)

`frontend_url`
[frontend_url](http://localhost:3000/)

> #### Initial default credentials
>
> **userid** : 20110067  
> **password** : test
