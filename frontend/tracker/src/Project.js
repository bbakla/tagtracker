import React, {Component} from "react";
import {Tag} from "./tags/Tag";
import {Deployment} from "./deployment/Deployment";
import {ShowDependency} from "./dependencies/ShowDependency";

export class Project extends Component {
    constructor(props) {
        super(props);

        this.state = {
            selectedTagIndex:0
        };
    }


    handleSelect = (event) => {
        let selectedTagName = event.target.value

        let index = this.props.tags.findIndex(t => (t.tagName === selectedTagName));

        this.setState({selectedTagIndex: index});

    }

    removeProject = () => {
        let confirmDelete = window.confirm("Are you sure deleting the tag?")
        if (confirmDelete) {
            this.props.removeProjectFromList(this.props.name)
        }

    }

    render() {

        const description = this.props.description.length === 0 ? <br/> : this.props.description
        const tagName = this.props.tags.length === 0 ? "" : this.props.tags[this.state.selectedTagIndex].tagName
        const tagList = this.props.tags.length === 0 ? [] : this.props.tags
        const deployments = this.props.tags.length === 0 ? [] : this.props.tags[this.state.selectedTagIndex].deployments
        const dependentToMe = this.props.tags.length === 0 ? [] : this.props.tags[this.state.selectedTagIndex].dependentToMe
        const dependentOn =  this.props.tags.length === 0 ? [] : this.props.tags[this.state.selectedTagIndex].dependentOn


        return (
            <div key={this.props.name} className="col-md-6">

                <div className="card p-1">
                    <h5 className=" card-header">{this.props.name}
                    <button className="outline-danger float-right btn-sm" onClick={this.removeProject}> <i className=" far fa-trash-alt"></i></button>
                    </h5>

                    <div className="card-body">

                        <select className="form-control col-md-4"
                                onChange={this.handleSelect}>

                            {
                                this.props.tags.map(tag => (
                                    <option key={tag.tagName} value={tag.tagName}>{tag.tagName} </option>
                                ))
                            }
                        </select>

                        <div>
                        <p className= "card-title font-weight-bold">{description}</p>

                        </div>

                        <div className="row">
                            <div className="col-xs-6 col-md-6">

                                <Tag currentTagName = {tagName}
                                     projectName={this.props.name}
                                     projectId = {this.props.projectId}
                                     tags={tagList} />

                                <Deployment deploymentStatus={deployments}
                                            projectId = {this.props.projectId}/>
                            </div>
                            <div className="col-xs-6 col-md-6">
                                <ShowDependency relationshipType="dependentToMe"
                                                dependencies={dependentToMe}
                                                projectName={this.props.name}
                                                projectId = {this.props.projectId}
                                />
                                <ShowDependency relationshipType="dependentOn"
                                                dependencies={dependentOn}
                                                projectName={this.props.name}
                                                projectId = {this.props.projectId}
                                />
                            </div>
                        </div>
                    </div>
                 {/*   <ButtonGroup className="p-3" toggle={true}>
                        <Button variant="outline-info"> <i className="fas fa-edit"></i></Button>
                        <Button variant="outline-warning"><i className="fa fa-plus"></i></Button>
                        <Button variant="outline-danger"> <i className="far fa-trash-alt"></i></Button>

                    </ButtonGroup>*/}
                </div>

            </div>
        )
    }
}
