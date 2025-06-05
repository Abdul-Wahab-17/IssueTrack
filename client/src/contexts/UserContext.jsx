/* eslint-disable react-refresh/only-export-components */
import { createContext , useState , useEffect } from "react";
import axios from "axios";


export const UserContext = createContext();

export default function UserProvider({ children }) {
    const [username, setUsername] = useState("");
    const [email, setEmail] = useState("");
    const [role, setRole] = useState("");
    const [authenticated, setAuthenticated] = useState(false);

    function clearContext(){
        setUsername("");
        setEmail("");
        setRole("");
        setAuthenticated(false);
    }

    useEffect(() => {
        axios.get("http://localhost:8080/api/users/me", { withCredentials: true })
            .then(res => {
                console.log(res.data)
                setUsername(res.data.username);
                setEmail(res.data.email);
                setRole(res.data.role);
                setAuthenticated(true);
            })
            .catch(() => {
                console.log("User not logged in or session expired");
                clearContext();
            })

    }, []);

    return (
        <UserContext.Provider value={{ username, email , role , authenticated , setUsername , setEmail , setRole , setAuthenticated , clearContext }}>
            {children}
        </UserContext.Provider>
    );
}
