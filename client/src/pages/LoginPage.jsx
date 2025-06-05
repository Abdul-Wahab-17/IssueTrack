import axios from 'axios';
import React ,{ useContext, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { UserContext } from '../contexts/UserContext';

const LoginPage = () =>{

    const {setUsername , setEmail , setRole , setAuthenticated} = useContext(UserContext);

    const Navigate = useNavigate();

    const [formData, setFormData] = useState( {
        "username":"",
        "password":""
    })

   function handleChange(e){
    const {name , value} = e.target;
   setFormData( prev=> ({...prev,[name]:value}))
   }

  function handleSubmit(e) {
    e.preventDefault();
    axios.post('http://localhost:8080/api/auth/login', formData, { withCredentials: true })
        .then((res) => {
            setUsername(res.data.username)
            setRole(res.data.role)
            setEmail(res.data.email)
            setAuthenticated(true)

            Navigate(`/dashboard`);
        })
        .catch(() => {
                window.alert("Invalid credentials");
        });
}


    return (
        <>
            <form onSubmit={ (e)=>{ handleSubmit(e)}}>
                <label>Username: </label>
                <input type="text" name="username" onChange={(e)=>{handleChange(e)}}/>
                <label>Password: </label>
                <input type="password" name="password" onChange={(e)=>{handleChange(e)}}/>
                <input type="submit" value={'submit'}/>
            </form>
        </>
    )
}

export default LoginPage;