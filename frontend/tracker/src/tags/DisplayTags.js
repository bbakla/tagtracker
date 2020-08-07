import React, {useContext, useEffect, useState} from "react";

import TagFormModal from "./TagFormModal";
import {Table} from "react-bootstrap";
import axios from 'axios';
import {basePathForProjects, tagBasePath, deleteTagPath} from "../paths";
import {useLocation} from "react-router";
import {ProjectContext___} from "../ProjectDashboard";

export default function DisplayTags() {

    let location = useLocation();
    const [tagsToDisplay, setTagsToDisplay] = useState(location.state.tags)

    const deleteTag = (tagName) => {
        let confirmDelete = window.confirm("Are you sure deleting the tag?")
        if (confirmDelete) {
            setTagsToDisplay(tagsToDisplay.filter(
                tag => tag.tagName !== tagName));

            let path = deleteTagPath.replace("{identifier}",
                location.state.projectId)

            path = path.replace("{tagName}", tagName).replace("{isRemoteTagDeleted}", "true");

            const deleteTag = async () => {
                axios.delete(path,
                ).then(response => console.log(response.data))
            };

            deleteTag();
        }

    }

    const saveTagHandle = (tag) => {
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
            let path = tagBasePath.replace("{identifier}",
                location.state.projectId)

            const body = JSON.stringify(newTag)

            axios.post(path,
                body,
                {
                    headers: {
                        'Content-Type': 'application/json',
                    }
                }
            ).then(response => console.log(response.data))

        };

        createTag();

        setTagsToDisplay(tags => [...tags, newTag])

    }

    const editTagHandle = (modifiedTag) => {
        const existingTag = tagsToDisplay.find(t => t.tagName === modifiedTag.name)

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

        setTagsToDisplay(newTags);
    }

    useEffect(() => {
        const fetchData = async () => {

            let path = tagBasePath.replace("{identifier}",
                location.state.projectId)

            const tags = await axios(path);
            setTagsToDisplay(tags.data);

        };

        fetchData();
    }, [])

    const tagsList = tagsToDisplay.map(tag => {

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
                                      saveTag={editTagHandle}
                        />
                        {' '}

                        <button type="button"
                                className="btn btn-sm btn-danger"
                                onClick={() => deleteTag(tag.tagName)}>
                            <i className=" far fa-trash-alt"></i>
                        </button>
                    </div>
                </td>
            </tr>

        )
    });

    return (

        <div className="container">
            <h3 className="mt-3">{location.state.projectName}</h3>
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
                        saveTag={saveTagHandle}

                    />
                </div>
            </div>
        </div>

    );
}
