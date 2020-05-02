import React, {Component} from "react";
import {Tag} from "./tags/Tag";
import {Deployment} from "./deployment/Deployment";
import {ShowDependency} from "./dependencies/ShowDependency";
import {DisplayTags} from "./tags/DisplayTags";
import {Switch, Route} from "react-router-dom";

export class Project extends Component {

  render = () =>

      <div key={this.props.name} className="col-md-6 mb-3">
        <div className="card p-1">
          <h5 className="card-header">{this.props.name}</h5>
          <div className="card-body">
            <p className="card-title font-weight-bold">Special title
              treatment</p>
            <p className="card-text">{this.props.description}</p>

            <div className="row">
              <div className="col-xs-6 col-md-6">

                <Tag projectName = {this.props.name}
                    tags = {this.props.tags}/>

                <Deployment tags = {this.props.deployments}/>
              </div>
              <div className="col-xs-6 col-md-6">
                <ShowDependency relationshipType = "dependentToMe" dependencies = {this.props.dependentToMe}/>
                <ShowDependency relationshipType = "dependentOn" dependencies = {this.props.dependentOn}/>
              </div>
            </div>
          </div>
        </div>

      </div>

}
