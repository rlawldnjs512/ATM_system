import React, { useEffect, useState } from 'react';
import { DataGrid } from '@mui/x-data-grid';
import EditATM from './EditATM';
import AddATM from './AddATM';

const ATMList = () => {

    const columns = [
        {field:'brand',headerName:'브랜드',width:150,headerAlign:'center'},
        {field:'model',headerName:'모델',width:150,headerAlign:'center'},
        {field:'productYear',headerName:'생산년도',width:150,headerAlign:'center'},
        {field:'price',headerName:'가격',width:150,headerAlign:'center'},
        {field:'statement',headerName:'명세표',width:150,headerAlign:'center'},
        {field:'interPhone',headerName:'인터폰',width:150,headerAlign:'center'},
        {field:'_links.self.href',headerName:'삭제',width:150,headerAlign:'center',sortable: false,filterable: false,disableColumnMenu: true,
            renderCell: row=>
                <button onClick={()=>{
                    onDeleteClick(row.id);
                }}>
                    삭제
                </button>
        },
        {field:'_links.aTM.href',headerName:'수정',width:150,headerAlign:'center',sortable: false,filterable: false,disableColumnMenu: true,
            renderCell: row=>
                <EditATM data={row} updateAtm={updateAtm}/>
        }
    ];

    const [atms,setAtms] = useState([]);
    const [open,setOpen] = useState(false);

    // 삭제
    const onDeleteClick = (url) => {
        const token = sessionStorage.getItem('jwt');
        fetch(url,
            {
                method:"DELETE",
                headers:{"Authorization":token}
            })
        .then(response=>{
            if(response.ok) {
                fetchAtm();
                setOpen(true);
            } else {
                alert("Delete Failed");
            }
        })
        .then()
        .catch()
    }

    const fetchAtm = () => {
        const token = sessionStorage.getItem('jwt');
        fetch('http://localhost:8088/api/machines',{
            headers:{"Authorization":token}
        })
        .then(response => response.json())
        .then(data => {
            setAtms(data._embedded.aTMs)
        })
        .catch(error => console.log(error))
    }

    useEffect(() => {
        fetchAtm();
    },[]);

    // 수정
    const updateAtm = (atm,link) => {
        const token = sessionStorage.getItem('jwt');
        fetch(link,{
            method:"PUT",
            headers:{'Content-Type':'application/json','Authorization':token},
            body:JSON.stringify(atm)
        })
        .then(response => {
            if(response.ok) {
                fetchAtm()
            } else {
                alert("Update Failed")
            }
        })
        .catch(error => console.log(error))
    }

    // 추가
    const addAtm = (atm) => {
        const token = sessionStorage.getItem('jwt');
        fetch('http://localhost:8088/api/machines',{
            method:"POST",
            headers:{'Content-Type':'application/json','Authorization':token},
            body:JSON.stringify(atm)
        })
        .then(response => {
            if(response.ok) {
                fetchAtm()
            } else {
                alert("Add Failed")
            }
        })
        .catch(error => console.log(error))
    }

    return (
        <div>
            <AddATM addAtm={addAtm}/>
            <DataGrid rows={atms}
                      columns={columns}
                      getRowId={row => row._links.self.href}>

            </DataGrid>
        </div>
    );
};

export default ATMList;