import { Button, Dialog, DialogActions, DialogContent, DialogTitle } from '@mui/material';
import React, { useState } from 'react';

const EditATM = (props) => {

    const [open,setOpen] = useState(false);

    const [atm,setAtm] = useState({
        brand:'',
        model:'',
        productYear:'',
        price:'',
        statement:'',
        interPhone:''
    })

    const handleOpen = () => {
        setAtm({
            brand:props.data.row.brand,
            model:props.data.row.model,
            productYear:props.data.row.productYear,
            price:props.data.row.price,
            statement:props.data.row.statement,
            interPhone:props.data.row.interPhone
        });
        setOpen(true);
    }

    const handleClose = () => {
        setOpen(false);
    }

    const handleChange = (event) => {
        setAtm({...atm,[event.target.name]:event.target.value})
    }

    const handleSave = () => {
        props.updateAtm(atm, props.data.id);
        handleClose();
    }

    return (
        <div>
            <Button onClick={handleOpen}>수정</Button>
            <Dialog open={open} onClose={handleClose}>
                <DialogTitle>ATM 정보수정</DialogTitle>
                <DialogContent>
                    <input name='brand' value={atm.brand} onChange={handleChange} placeholder='브랜드'/><br/>
                    <input name='model' value={atm.model} onChange={handleChange} placeholder='모델'/><br/>
                    <input name='productYear' value={atm.productYear} onChange={handleChange} placeholder='생산년도'/><br/>
                    <input name='price' value={atm.price} onChange={handleChange} placeholder='가격'/><br/>
                    <input name='statement' value={atm.statement} onChange={handleChange} placeholder='명세표'/><br/>
                    <input name='interPhone' value={atm.interPhone} onChange={handleChange} placeholder='인터폰'/><br/>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose}>수정 취소</Button>
                    <Button onClick={handleSave}>수정 저장</Button>
                </DialogActions>
            </Dialog>
        </div>
    );
};

export default EditATM;