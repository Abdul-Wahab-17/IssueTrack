import axios from "axios";
import { useContext } from "react";
import { useNavigate  } from "react-router-dom"
import { UserContext } from "../contexts/UserContext";

export default function LogoutPage(){

    const {clearContext} = useContext(UserContext);

const Navigate = useNavigate();

function handleLogout(){
    axios.post("http://localhost:8080/api/auth/logout" , {} ,{withCredentials:true})
    .then( (res)=>{
        console.log(res)
        clearContext();
        Navigate('/login')
    } )
}

    return (
        <>
            Are you sure you want to log out?
            <button onClick={handleLogout}>Yes</button>
            <button onClick={()=>{Navigate('/login')}}>No</button>
        </>
    )
}