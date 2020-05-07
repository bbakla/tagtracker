import React, {Component} from "react";
import {Link} from "react-router-dom";

export class Tag extends Component {
    constructor(props) {
        super(props);

        this.state = {
            showTagOptions: false,
        }
    }

    render() {
        //const pathName = "/projects/:projectId/tags".replace(":projectId", this.props.projectId); //1. approach
        const pathName = (projectId) => `/projects/${projectId}/tags`; // 2.approach

        return (
            <div>
                <Link className="btn btn-outline-success btn-lg btn-block mb-2" to={{
                    pathname: pathName(this.props.projectId),
                    state: {
                        tags: this.props.tags,
                        projectName: this.props.projectName
                    }
                }}>
         <span>
            <span className="fa fa-tag p-2"></span>
            <span className="badge badge-light">{this.props.currentTagName}</span>
        </span>
                    <br/>Tags
                </Link>
            </div>

        );
    }


}
