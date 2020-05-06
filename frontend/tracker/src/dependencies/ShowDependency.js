import React, {Component} from "react";
import {Link} from "react-router-dom";

export class ShowDependency extends Component {

    render() {
        const buttonName = this.props.relationshipType === "dependentOn" ? "DependentOn" : "DependentToMe";
        const dependencyType = this.props.relationshipType === "dependentOn" ? "Depends on " + this.props.projectName : "Projects dependent on " + this.props.projectName

        return (
            <div>
                <Link className="btn btn-outline-info btn-lg btn-block mb-2" to={{
                    pathname: `/projects/${this.props.relationshipType}`,
                    state: {
                        dependencies: this.props.dependencies,
                        dependencyType: dependencyType
                    }
                }}>

                    <span className="fa fa-caret-right p-2"></span>
                    <br/>{buttonName}
                </Link>
            </div>);
    }


}
