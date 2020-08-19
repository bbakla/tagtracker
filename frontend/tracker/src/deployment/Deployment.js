import React, {useState} from "react";

import {Modal} from "react-bootstrap";
import Button from "react-bootstrap/Button";

export default function Deployment({deploymentStatus, projectId, tagName}) {

  const [showDeployStatus, setShowDeployStatus] = useState(false);
  const [deployments, setDeployments] = useState(deploymentStatus);

  const close = () => {
    setShowDeployStatus(false);
  }

  const showDeploymentStatus = () => {

    if (showDeployStatus) {
      setShowDeployStatus(false);

    } else {
      setShowDeployStatus(true);
    }
  }

  const deploy = (name, value) => {
    console.log(value);
    console.log(name);

  }

  const inProgress = "pipelineRunning spinner-border  text-primary";

  const status = Object.keys(deploymentStatus).map((key, value) => {
        return (
            <div key={key + "_" + value} className="row">
              <i className={deploymentStatus[key] === "true"
                  ? "far fa-check-circle pipelinePass text-success pr-2"
                  : "far fa-times-circle pipelineFails text-danger pr-2"}/>
              <label className="switch">
                <input type="checkbox" name={key}
                       checked={deploymentStatus[key] === "true"}
                       onChange={e => deploy(e.target.name,
                           deploymentStatus[key])}/>
                <span className="slider round"></span>
              </label>
              <label className="pl-2">{key}</label>
            </div>
        )
      }
  );


    return (
        <div>
          <button className="btn btn-outline-warning btn-lg btn-block mb-2"
                  onClick={showDeploymentStatus}>
            <span className="fa fa-plug p-2"></span>
            <br/>Deployments
          </button>

          <div>
            <Modal show={showDeployStatus} onHide={close}>
              <Modal.Header>Deployment status</Modal.Header>
              <Modal.Body>

                <div className="custom-control custom-switch">
                  {status}

                </div>
              </Modal.Body>
              <Modal.Footer>
                <Button onClick={close}>Close</Button>
              </Modal.Footer>

            </Modal>
          </div>
        </div>
    );

}


