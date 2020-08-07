import React, {useContext, useState} from "react";
import {basePathForProjects} from "./paths";
import axios from 'axios';
import {ProjectContext___} from "./ProjectDashboard";

export default function AddProject({addProject}){
    const [projectIdentifier, setProjectIdentifier] = useState("");

    const  saveIdentifier = (event) => {
        setProjectIdentifier(event.target.value);
    }

    const handleAddProject = () => {
      const body = {
        "identifier": projectIdentifier
      }

      axios.post(basePathForProjects, body).then(
          response => addProject(response.data))

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
