import React, {Component} from "react";
import {Tag} from "./tags/Tag";
import {Deployment} from "./deployment/Deployment";
import {ShowDependency} from "./dependencies/ShowDependency";
import {DisplayTags} from "./tags/DisplayTags";
import {Switch, Route} from "react-router-dom";
import Dropdown from "react-bootstrap/Dropdown";
import DropdownButton from "react-bootstrap/DropdownButton";
import ButtonGroup from "react-bootstrap/ButtonGroup";

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
                      {/*    <Dropdown>
                        <Dropdown.Toggle variant="outline-danger" id="dropdown-basic">
                           Tags
                        </Dropdown.Toggle>

                        <Dropdown.Menu>
                            {
                                this.props.tags.map(tag => (
                                    <Dropdown.Item as="button" onClick={() => this.handleSelect({tag})}>{tag.tagName}</Dropdown.Item>
                                ))
                            }
                        </Dropdown.Menu>
                    </Dropdown>*/}

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

                                <Tag projectName={this.props.name}
                                     tag={this.props.tags[this.state.selectedTagIndex]}/>

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
                </div>

            </div>
        )
    }
}
