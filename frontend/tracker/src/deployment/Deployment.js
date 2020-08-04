import React, {useState} from "react";

import {Modal} from "react-bootstrap";
import Button from "react-bootstrap/Button";

export default function Deployment({deploymentStatus, projectId}){

    const[showDeployStatus, setShowDeployStatus] = useState(false);


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

  const deploy = (event) => {
    console.log(event.target.value);

  }

    const inProgress = "pipelineRunning spinner-border  text-primary";

    const status = () => {
        if (!Object.hasOwnProperty(deploymentStatus)) {
            Object.keys(deploymentStatus).map((key, value) =>

                <div key={key} className="row">
                    <i className={deploymentStatus[key] === "true" ? "far fa-check-circle pipelinePass text-success pr-2" : "far fa-times-circle pipelineFails text-danger pr-2"}/>
                    <label className="switch">
                        <input type="checkbox" checked={deploymentStatus[key] === "true"} onChange={deploy}/>
                        <span className="slider round"></span>
                    </label>
                    <label className="pl-2">{key}</label>
                </div>
            );
        }
    }


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
                    {Object.hasOwnProperty(deploymentStatus)} && {status}

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


