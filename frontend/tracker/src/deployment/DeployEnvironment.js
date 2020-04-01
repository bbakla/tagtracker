import React, {Component} from "react";

export class DeployEnvironment extends Component {

  render() {
    return (
        <div className="custom-control custom-switch">
          <div className="row">
            <a className="far fa-check-circle pipelinePass text-success pr-2"
               href="#">

            </a>


            <label className="switch">
              <input type="checkbox"/>
              <span className="slider round"></span>
            </label>
            <label className="pl-2">dev</label>
          </div>

          <div className="row">
            <a className="far fa-times-circle pipelineFails text-danger pr-2"
               href="#"></a>
            <label className="switch">
              <input type="checkbox"/>
              <span className="slider round"></span>
            </label>
            <label className="pl-2">int</label>
          </div>

          <div className="row">
            <a className="pipelineRunning spinner-border  text-primary"
               role="status">
              <span className="sr-only">Loading...</span>
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
