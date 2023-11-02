import List from "@mui/material/List";
import ListItem from "@mui/material/ListItem";
import ListItemButton from "@mui/material/ListItemButton";
import ListItemText from "@mui/material/ListItemText";
import Box from "@mui/material/Box";
import Link from "@mui/material/Link";

export default function NavigationList(links: LinkName[], toggleDrawer: any) {
    return (
        <Box
            sx={{
                width: 250,
            }}
            role="presentation"
            onClick={toggleDrawer(false)}
            onKeyDown={toggleDrawer(false)}
        >
            <List>
                {links.map((link, id) => (
                    <Link key={id} href={link.link} underline="none">
                        <ListItem disablePadding>
                            <ListItemButton>
                                <ListItemText primary={link.name} />
                            </ListItemButton>
                        </ListItem>
                    </Link>
                ))}
            </List>
        </Box>
    );
}
