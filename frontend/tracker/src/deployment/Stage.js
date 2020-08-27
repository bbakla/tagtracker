import React from "react"

export default function Stage({stage}) {

 const jobStatusIcon = (status) => {
     if( status === "success")  {
         return "far fa-check-circle  color-green fa-lg build-content ci-status-icon ";
     } else if (status === "manual") {
         return "fa fa-cog fa-lg  job build-content ci-status-icon";
     } else if (status ==="failed") {
         return "far fa-times-circle btn-outline-danger ci-status-icon fa-lg build-content";
     } else {
         return "";
     }
 }

 const jobActionIcon = (status) => {
     if( status === "success" || status === "failed")  {
         return "fas fa-sync  btn-blank btn-transparent ci-action-icon-container ";
     } else if (status === "manual") {
         return "fas fa-play  btn-blank btn-transparent ci-action-icon-container ";
     } else {
         return "";
     }
 }

    return(
        <div className="">
            {stage.jobResources.map(j => {

                return(
                    <div key= {j.jobId} className=" pipeline-graph ">
                        <ul key={j.jobId} className="job-list">
                            <li className="build">
                                <div className="ci-job-component">


                                    <span>
                                        <span>
                                            <i className={jobStatusIcon(j.status)}/>
                                        </span>
                                        <span className="ci-status-text w-100 ml-2 mr-3 text-truncate job-font">
                                            {j.name}
                                        </span>

                                    </span>

                                    <button type="button" className=" job-action">
                                        <i className={jobActionIcon(j.status)}></i>
                                    </button>
                                </div>
                            </li>
                        </ul>
                </div>
                   )
            })}
            </div>


    );
}

