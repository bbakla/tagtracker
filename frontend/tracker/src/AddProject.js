import React, {useContext, useState} from "react";
import {basePathForProjects} from "./paths";
import axios from 'axios';
import {GlobalContext} from "./Store";


export default function AddProject(){
    const [projectIdentifier, setProjectIdentifier] = useState("");
    const {addRepository} = useContext(GlobalContext)

    const  saveIdentifier = (event) => {
        setProjectIdentifier(event.target.value);
    }

    const handleAddProject = () => {
     addRepository(projectIdentifier)

      setProjectIdentifier("");


    }

  return (
      <div className="input-group mb-2 ">
        <input type="text" name="projectIdentifier" className="form-control" value={projectIdentifier} onChange={saveIdentifier}/>
        <button className="btn-md btn-outline-secondary" type="submit" onClick={handleAddProject}>Add
          repository
        </button>

      </div>);

}
