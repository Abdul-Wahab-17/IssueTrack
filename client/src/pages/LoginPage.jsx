import axios from 'axios';

const LoginPage = () =>{

    const loginUrl = process.env.URL + '/api/auth/login';


    function login(){
        async ()=>{
            await axios.post( loginUrl , )
        }
    }

    return (
        <>
            <form onSubmit={login}>
                <label>Username: </label>
                <input type="text" name="username"/>
                <label>Password: </label>
                <input type="password" name="password"/>
                <input type="submit"/>
            </form>
        </>
    )
}

export default LoginPage;