import { useContext } from "react";
import { UserContext } from "../contexts/UserContext";

function Dashboard() {
    const { username , email , role , authenticated } = useContext(UserContext);

    if (!authenticated) return <div>Please log in</div>;

    return (
        <>
            <h2>Welcome, {username}!</h2>
            <p>Email: {email}</p>
            <p>Role: {role}</p>
        </>
    );
}

export default Dashboard;
