import React, {Component} from "react";

export class AddProject extends Component {

    constructor(props) {
        super(props);

        this.state = {
            projectIdentifier: ""
        }
    }

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

    saveIdentifier = (event) => {
        this.setState({[event.target.name] : event.target.value});
    }

    addProject = () => {
        this.props.addProject({
            projectName: this.state.projectIdentifier,
            projectId: Math.floor(Math.random() * 10)
        })
    }

  render = () =>
      <div className="input-group mb-2 ">
        <input type="text" name="projectIdentifier" className="form-control" defaultValue={this.state.projectIdentifier} onChange={this.saveIdentifier}/>
        <button className="btn-md btn-outline-secondary" type="submit" onClick={this.addProject}>Add
          repository
        </button>

      </div>

}
