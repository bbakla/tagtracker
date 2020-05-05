import React, {Component} from "react";

export class DeployEnvironment extends Component {

  render() {

      console.log("in DeployEnvironment");
      console.log(this.props.deploymentStatus);

      const classNameForDev = this.props.deploymentStatus.dev === "true" ? "far fa-check-circle pipelinePass text-success pr-2" : "far fa-times-circle pipelineFails text-danger pr-2";
      const classNameForInt = this.props.deploymentStatus.int === "true" ? "far fa-check-circle pipelinePass text-success pr-2" : "far fa-times-circle pipelineFails text-danger pr-2";
      const classNameForProd = this.props.deploymentStatus.prod === "true" ? "far fa-check-circle pipelinePass text-success pr-2" : "far fa-times-circle pipelineFails text-danger pr-2";
    const inProgress = "pipelineRunning spinner-border  text-primary";

    return (
        <div className="custom-control custom-switch">
          <div className="row">
            <a className={classNameForDev}
               href="#">
            </a>
            <label className="switch">
              <input type="checkbox"/>
              <span className="slider round"></span>
            </label>
            <label className="pl-2">dev</label>
          </div>

          <div className="row">
            <a className= {classNameForInt}
               href="#"></a>
            <label className="switch">
              <input type="checkbox"/>
              <span className="slider round"></span>
            </label>
            <label className="pl-2">int</label>
          </div>

          <div className="row">
            <a className= {classNameForProd}
               role="status">
              <span className="sr-only"></span>
            </a>
            <label className="switch">
              <input type="checkbox"/>
              <span className="slider round"></span>
            </label>
            <label className="pl-2">prod</label>
          </div>

        </div>




        /* <input
             type='checkbox'
             className='custom-control-input'
             id='customSwitchesChecked'
             defaultChecked
         />
         <label className='custom-control-label' htmlFor='customSwitchesChecked'>dev</label>*/

    );
  }

}
