import React, {useContext} from "react"
import {GlobalContext} from "../Store";

export default function Stage({jobs, stageName, projectId, tagName}) {

  const {runJob} = useContext(GlobalContext)

  const jobStatusIcon = (status) => {
    if (status === "success") {
      return "far fa-check-circle  color-green fa-lg build-content ci-status-icon ";
    } else if (status === "manual") {
      return "fa fa-cog fa-lg  job build-content ci-status-icon";
    } else if (status === "failed") {
      return "far fa-times-circle btn-outline-danger ci-status-icon fa-lg build-content";
    } else {
      return "";
     }
 }

  const jobActionIcon = (status) => {
    if (status === "success" || status === "failed") {
      return "fas fa-sync  btn-blank btn-transparent ci-action-icon-container ";
    } else if (status === "manual") {
      return "fas fa-play  btn-blank btn-transparent ci-action-icon-container ";
    } else {
      return "";
    }
  }

  const handleJob = (job) => {

    const jobDto = {
      stage: job.stage,
      name: job.name,
      jobOperation: job.status === "manual" ? "play" : "retry"
    }

    runJob(job.jobId, projectId, tagName, jobDto)

  }

  return (
      <div className="">
        {jobs.jobResources.map(j => {

          return (
              <div key={j.jobId} className=" pipeline-graph ">
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

                      <button type="button" className=" job-action"
                              onClick={() => handleJob(j)}>
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

