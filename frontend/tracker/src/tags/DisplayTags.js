import React, {useContext, useEffect, useState} from "react";

import TagFormModal from "./TagFormModal";
import {Table} from "react-bootstrap";
import {useLocation} from "react-router";
import {GlobalContext} from "../Store";

export default function DisplayTags() {
  let location = useLocation();
  const {createTag, deleteTag, projects} = useContext(GlobalContext)

  const getTags = () => {
    const project = projects.find(
        project => project.projectId === location.state.projectId).tags
    return project;
  }

  const [tagsToDisplay, setTagsToDisplay] = useState(() => getTags());

  const handleDeleteTag = (tagName) => {
    let confirmDelete = window.confirm("Are you sure deleting the tag?")
    if (confirmDelete) {
      setTagsToDisplay(tagsToDisplay.filter(
          tag => tag.tagName !== tagName));

      deleteTag(location.state.projectId, tagName);
    }

  }

    const saveTagHandle = (tag) => {
        const newTag = {
          tagName: tag.name,
          message: tag.message,
          releaseNotes: tag.releaseNotes,
          createdDate: new Date(),
          dependentOnMe: [],
          dependentOn: [],
          deployments:
              {
                dev: "",
                int: "",
                prod: ""
              }
        }

      createTag(location.state.projectId, newTag);
      setTagsToDisplay(tags => [...tags, newTag])

    }

    const editTagHandle = (modifiedTag) => {
        const existingTag = tagsToDisplay.find(t => t.tagName === modifiedTag.name)

      let newTags = tagsToDisplay.map(t => {
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
    setTagsToDisplay(() => getTags());

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
                              onClick={() => handleDeleteTag(tag.tagName)}>
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
