import React, {Component} from "react";

import {TagFormModal} from "./TagFormModal";
import {Table} from "react-bootstrap";
import axios from 'axios';
import {basePathForProjects, createTagPath, deleteTagPath} from "../paths";

export class DisplayTags extends Component {
    constructor(props) {
        super(props);

        this.state = {
            showModal: false,
            tagsToDisplay: this.props.location.state.tags
        }
    }


    deleteTag = (tagName) => {
        let confirmDelete = window.confirm("Are you sure deleting the tag?")
        if (confirmDelete) {
            this.setState({
                tagsToDisplay: this.state.tagsToDisplay.filter(
                    tag => tag.tagName !== tagName)
            });

            const deleteTag = async () => {
                const deletedTag = await axios(
                    deleteTagPath.replace("{identifier}",
                        this.props.location.state.projectName).replace(
                        "{tagName}", tagName));
                console.log(deletedTag)
            };

            deleteTag();
        }

    }

    saveTagHandle = (tag) => {
        const newTag = {
            tagName: tag.name,
            message: tag.message,
            releaseNotes: tag.releaseNotes,
            createdDate: new Date(),
            dependentToMe: [],
            dependentOn: [],
            deployments:
                {
                    dev: "",
                    int: "",
                    prod: ""
                }

        }

        const createTag = async () => {
            let path = createTagPath.replace("{identifier}",
                this.props.location.state.projectName)
            console.log(path);

            const body = JSON.stringify(newTag)
            console.log(body)

            axios.post("/projects/116955/tags",
                body,
                {
                    headers: {
                        'Content-Type': 'application/json',
                    }
                }
            ).then(response => console.log(response.data))

        };

        createTag();

        this.setState({tagsToDisplay: this.state.tagsToDisplay.concat(newTag)});

    }

    editTagHandle = (modifiedTag) => {
        const existingTag = this.state.tagsToDisplay.find(t => t.tagName === modifiedTag.name)

        let newTags = this.state.tagsToDisplay.map(t => {
            if (t.tagName === modifiedTag.name) {

                return Object.assign({}, t,
                    {
                        tagName: existingTag.tagName,
                        message: existingTag.message,
                        releaseNotes: modifiedTag.releaseNotes,
                        /*  createdDate: existingTag.createdDate,
                          dependentToMe: existingTag.dependentToMe,
                          dependentOn: existingTag.dependentOn,
                          deployments: existingTag.deployments*/
                    });
            } else {
                return t;
            }
        });

        this.setState({tagsToDisplay: newTags});
    }

    render() {

        const tagsList = this.state.tagsToDisplay.map(tag => {

            return (
                <tr key={tag.tagName + "key"}>
                    <td>{tag.tagName}</td>
                    <td>{tag.message}</td>
                    <td>{tag.releaseNotes}</td>

                    <td>
                        <div>
                            <TagFormModal buttonLabel="Edit"
                                          tagName={tag.tagName}
                                          message={tag.message}
                                          releaseNotes={tag.releaseNotes}
                                          saveTag={this.editTagHandle}
                            />
                            {' '}

                            <button type="button"
                                    className="btn btn-sm btn-danger"
                                    onClick={() => this.deleteTag(tag.tagName)}>
                                <i className=" far fa-trash-alt"></i>
                            </button>
                        </div>
                    </td>
                </tr>

            )
        });

        return (


            <div className="container">
                <h3 className="mt-3">{this.props.location.state.projectName}</h3>
                <div className="row">
                    <div className="col-12">
                        <Table responsive hover>
                            <thead>
                            <tr>
                                <th>Tag Name</th>
                                <th>Message</th>
                                <th>Release Note</th>

                            </tr>
                            </thead>

                            <tbody>
                            {tagsList}
                            </tbody>
                        </Table>

                        <TagFormModal
                            buttonLabel="Create"
                            saveTag={this.saveTagHandle}
                        />
                    </div>
                </div>
            </div>

        );
    }
}
