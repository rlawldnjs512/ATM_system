import { Box, Button, Dialog, DialogActions, DialogContent, DialogTitle, Stack, TextField } from '@mui/material';
import React, { useState } from 'react';

const AddATM = (props) => {
    const [open,setOpen] = useState(false);
    const [atm,setAtm] = useState({
        brand:'',
        model:'',
        productYear:'',
        price:'',
        statement:'',
        interPhone:''
    });

    const handleClickOpen = () => {
        setOpen(true);
    }

    const handleClickClose = () => {
        setOpen(false);
        setAtm({
            brand:'',
            model:'',
            productYear:'',
            price:'',
            statement:'',
            interPhone:''
        });
    }

    const handleChange = (event) => {
        setAtm({...atm,[event.target.name]:event.target.value})
    }

    const handleClickSave = () => {
        props.addAtm(atm);
        handleClickClose();
    }

    return (
        <div>
            <Button onClick={handleClickOpen} variant='contained' color='info'>ATM 등록</Button>
            <Dialog open={open} onClose={handleClickClose}>
                <DialogTitle align='center'>ATM 등록정보</DialogTitle>
                <DialogContent>
                    <Box sx={{padding:2}}>
                        <Stack spacing={2}>
                            <TextField fullWidth label='brand' name='brand' value={atm.brand} onChange={handleChange} variant='standard'/>
                            <TextField fullWidth label='model' name='model' value={atm.model} onChange={handleChange} variant='standard'/>
                            <TextField fullWidth label='productYear' name='productYear' value={atm.productYear} onChange={handleChange} variant='standard'/>
                            <TextField fullWidth label='price' name='price' value={atm.price} onChange={handleChange} variant='standard'/>
                            <TextField fullWidth label='statement' name='statement' value={atm.statement} onChange={handleChange} variant='standard'/>
                            <TextField fullWidth label='interPhone' name='interPhone' value={atm.interPhone} onChange={handleChange} variant='standard'/>
                        </Stack>
                    </Box>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClickClose} color='error' variant='outlined'>등록 취소</Button>
                    <Button onClick={handleClickSave} color='secondary' variant='outlined'>등록 저장</Button>
                </DialogActions>
            </Dialog>
        </div>
    );
};

export default AddATM;