import React, {Component} from "react";

import {Modal} from "react-bootstrap";
import Button from "react-bootstrap/Button";

export class Deployment extends Component {
  constructor(props) {
    super(props);

    this.state = {
      showDeployStatus: false,
    }
  }

  close = () => {
    this.setState({showDeployStatus: false});
  }

  showDeploymentStatus = () => {

    if (this.state.showDeployStatus) {
      this.setState({showDeployStatus: false});

    } else {
      this.setState({showDeployStatus: true});
    }
  }

  deploy = (event) => {
    console.log(event.target.value);

  }

  render() {
    const inProgress = "pipelineRunning spinner-border  text-primary";


    debugger;
    const status = Object.keys(this.props.deploymentStatus).map((key, value) =>

        <div key={key} className="row">
            <i className={this.props.deploymentStatus[key] === "true" ? "far fa-check-circle pipelinePass text-success pr-2" : "far fa-times-circle pipelineFails text-danger pr-2"}/>
            <label className="switch">
                <input type="checkbox" checked={this.props.deploymentStatus[key] === "true"} onChange={this.deploy}/>
                <span className="slider round"></span>
            </label>
            <label className="pl-2">{key}</label>
        </div>
    );

    return (
        <div>
          <button className="btn btn-outline-warning btn-lg btn-block mb-2"
                  onClick={this.showDeploymentStatus}>
            <span className="fa fa-plug p-2"></span>
            <br/>Deployments
          </button>

          <div>
            <Modal show={this.state.showDeployStatus} onHide={this.close}>
              <Modal.Header>Deployment status</Modal.Header>
              <Modal.Body>

                <div className="custom-control custom-switch">
                  {status}

                </div>
              </Modal.Body>
              <Modal.Footer>
                <Button onClick={this.close}>Close</Button>
              </Modal.Footer>

            </Modal>
        </div>
        </div>
    );
  }

}


