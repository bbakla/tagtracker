import React, {Component} from "react";
import {DeployStatusForm} from "./DeployStatusForm";

export class Deployment extends Component {
  constructor(props) {
    super(props);

    this.state = {
      showDeployStatus: false,
    }
  }

  showDeploymentStatus = () => {
    if (this.state.showDeployStatus) {
      this.setState({showDeployStatus: false});
    } else {
      this.setState({showDeployStatus: true});
    }
  }

  render() {
    return (
        <div>
          <button className="btn btn-outline-warning btn-lg btn-block mb-2"
                  onClick={this.showDeploymentStatus}>
            <span className="fa fa-plug p-2"></span>

            <br/>Deployments
          </button>
          {this.state.showDeployStatus && <DeployStatusForm/>}
        </div>
    );
  }

}


