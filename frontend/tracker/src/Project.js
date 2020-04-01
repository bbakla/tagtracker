import React, {Component} from "react";
import {Tag} from "./tags/Tag";
import {Deployment} from "./deployment/Deployment";
import {ShowDependency} from "./dependencies/ShowDependency";

export class Project extends Component {

  render = () =>
      <div className="col-md-6 mb-3">
        <div className="card p-1">
          <h5 className="card-header">TicketEngine</h5>
          <div className="card-body">
            <p className="card-title font-weight-bold">Special title
              treatment</p>
            <p className="card-text">With supporting text below as a natural
              lead-in to additional content.</p>

            <div className="row">
              <div className="col-xs-6 col-md-6">
                <Tag/>
                <Deployment/>
              </div>
              <div className="col-xs-6 col-md-6">
                <ShowDependency relationshipType = "dependentToMe"/>
                <ShowDependency relationshipType = "dependentOn"/>
              </div>
            </div>
          </div>
        </div>
      </div>

}
