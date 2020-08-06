import React, {useState} from "react";
import {basePathForProjects} from "./paths";
import axios from 'axios';

export default function AddProject({addProject}){
    const [projectIdentifier, setProjectIdentifier] = useState("");

  /*constructor(props) {
      super(props);
      this.state = {
          repoInfo:{name: "", identifier: "", tags:[{tagName:""}]},
          appIdentifier:""
      }
  }

  updateAppIdentifier = (event) => {
      this.setState({
          appIdentifier : event.target.value
      })
  }

  getRepoInfo = () => {
      this.state = {
          repoInfo:{name: "newAppRepo", identifier: this.state.appIdentifier, tags: [{tagName:"tagInApp"}]
      }}

      this.props.callback(this.state.repoInfo)

      this.setState({
          repoInfo: {name: "", identifier: "", tags:[]},
          appIdentifier:""
      })
  }

  render = () =>
      <div className="my-1">
          <input className="form-control" value={this.state.appIdentifier}
          onChange={this.updateAppIdentifier}/>

          <button className="btn btn-primary mt-1"
          onClick={this.getRepoInfo}> Add</button>
      </div>
   */

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
        <input type="text" name="projectIdentifier" className="form-control" defaultValue={projectIdentifier} onChange={saveIdentifier}/>
        <button className="btn-md btn-outline-secondary" type="submit" onClick={handleAddProject}>Add
          repository
        </button>

      </div>);

}
