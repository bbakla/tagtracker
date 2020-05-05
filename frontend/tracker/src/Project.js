import React, {Component} from "react";
import {Tag} from "./tags/Tag";
import {Deployment} from "./deployment/Deployment";
import {ShowDependency} from "./dependencies/ShowDependency";
import {DisplayTags} from "./tags/DisplayTags";
import {Switch, Route} from "react-router-dom";
import Dropdown from "react-bootstrap/Dropdown";
import DropdownButton from "react-bootstrap/DropdownButton";
import ButtonGroup from "react-bootstrap/ButtonGroup";
import Button from "react-bootstrap/Button";

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

    render() {

        return (
            <div key={this.props.name} className="col-md-6">

                <div className="card p-1">
                    <h5 className="card-header">{this.props.name}</h5>

                    <div className="card-body">

                        <select className="form-control col-md-4"
                                onChange={this.handleSelect}>

                            {
                                this.props.tags.map(tag => (
                                    <option key={tag.tagName} value={tag.tagName}>{tag.tagName} </option>
                                ))
                            }
                        </select>

                        <p className="card-title font-weight-bold">{this.props.description}</p>

                        <div className="row">
                            <div className="col-xs-6 col-md-6">

                                <Tag currentTagName = {this.props.tags[this.state.selectedTagIndex].tagName}
                                     projectName={this.props.name}
                                     tags={this.props.tags}/>

                                <Deployment deploymentStatus={this.props.tags[this.state.selectedTagIndex].deployments}/>
                            </div>
                            <div className="col-xs-6 col-md-6">
                                <ShowDependency relationshipType="dependentToMe"
                                                dependencies={this.props.tags[this.state.selectedTagIndex].dependentToMe}/>
                                <ShowDependency relationshipType="dependentOn"
                                                dependencies={this.props.tags[this.state.selectedTagIndex].dependentOn}/>
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
