import React, { useState } from 'react';
import ATMList from './ATMList';
import { Button, TextField, Stack } from '@mui/material';

const Login = (props) => {

    const [user,setUser] = useState({
        username:'',
        password:''
    });

    const [isAuthenticationed,setAuth] = useState(false);

    const handleChange = (event) => {
        setUser({...user,[event.target.name]:event.target.value})
    }

    const login = () => {
        fetch('http://localhost:8088/login',{
            method : 'POST',
            headers : {'Content-Type':'application/json'},
            body : JSON.stringify(user)
        })
        .then(response => {
            const jwtToken = response.headers.get('Authorization');
            if(jwtToken !== null) {
                sessionStorage.setItem('jwt',jwtToken);
                setAuth(true);
                props.handleLogin();
            }
        })
        .catch(error => console.log(error))
    }

    if(isAuthenticationed) {
        return <div><ATMList/></div>
    } else {
        return (
            <div className='login-container'>
                <div className='login-box'>
                    <h2 className='login-title'>Login</h2>

                    <Stack spacing={2} alignItems={'center'} mt={2}>
                        <TextField name='username' label='UserName' onChange={handleChange}/>
                        <TextField name='password' label='Password' onChange={handleChange}/>
                        <button className='login-button' onClick={login}>로그인</button>
                    </Stack>
                </div>
            </div>
        );
    }


};

export default Login;