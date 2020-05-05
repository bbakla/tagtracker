import React, {Component} from "react";
import {DeployEnvironment} from "../deployment/DeployEnvironment";
import {RelatedProjects} from "./RelatedProjects";

export class ShowDependency extends Component {
    constructor(props) {
        super(props);

        this.state = {
            showDependencyTable : false
        }
    }
    showDependency = () => {
        if (this.state.showDependencyTable) {
            this.setState({showDependencyTable: false});
        } else {
            this.setState({showDependencyTable: true});
        }
    }

  render () {
        const buttonName = this.props.relationshipType == "dependentOn" ? "DependentOn" : "DependentToMe";

      return (<div>
          <a href="#" className="btn btn-outline-info btn-lg btn-block mb-2"
             role="button" onClick={this.showDependency}>
              <span className="fa fa-caret-right p-2"></span>
              <br/>{buttonName}</a>
          {this.state.showDependencyTable && <RelatedProjects dependencies = {this.props.dependencies}/>}
      </div>);

  }



}
