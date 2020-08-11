import React, {Component, useContext, useState} from "react";
import {Modal} from "react-bootstrap";
import Button from "react-bootstrap/Button";
import {GlobalContext} from "../Store";
import {DEPENDENT_ON, DEPENDENT_ON_ME} from "./dependency";

export default function DependencyModal({pName, tName, saveDependency, buttonLabel, relationshipType}) {

  const {projects} = useContext(GlobalContext);
  const [showModal, setShowModal] = useState(false);

  const [showProjects, setShowProjects] = useState(
      projects.filter(p => p.projectName !== pName));
  const [selectedProjectName, setSelectedProjectName] = useState(
      showProjects.length === 0 ? "" : showProjects[0].projectName);
  const [selectedTagName, setSelectedTagName] = useState(
      tName === undefined ? projects.find(
          p => p.projectName === selectedProjectName).tags[0].tagName : tName);


  console.log(selectedTagName);
  console.log(selectedProjectName);


  const open = () => {
    setShowModal(true);
  }

  const close = () => {
    setShowModal(false);
  }

  const handleSaveDependency = () => {

    saveDependency({
      projectName: selectedProjectName,
      tagName: selectedTagName
    });

    close();
  }

  const determineButtonLabel = () => {
    let label = buttonLabel;
    let button = '';

    if (label === "Edit") {
      label = "Save"
      button =
          <button className="float-left btn btn-sm btn-success" onClick={open}>
            <i className="fas fa-edit"></i>
          </button>
    } else {
      label = "Create"
      if (relationshipType === DEPENDENT_ON) {

        button = <Button className="btn btn-outline-primary btn-block"
                         onClick={open}>Create dependency</Button>
      }
    }

    return {label, button}
  }

  const handleSelectedProjectName = (value) => {
    setSelectedProjectName(value)
    const tagValue = showProjects.find(p => p.projectName === value).tags[0].tagName
    setSelectedTagName(tagValue)
  }

  return (
      <div>
        {determineButtonLabel().button}
        <Modal show={showModal} onHide={close}>
          <Modal.Header>Dependency</Modal.Header>
          <Modal.Body>
            <form>
              <div className="form-group">
                <label htmlFor="projectNameInput">Project Name</label>
                <select className="form-control col-md-4"
                        onChange={e => handleSelectedProjectName(e.target.value)}
                        value={selectedProjectName}>
                  {
                    showProjects.map(p => {
                      return (
                          <option key={p.projectName}
                                  value={p.projectName}>{p.projectName} </option>)
                    })
                  }
                </select>

              </div>

              <div className="form-group">
                <label htmlFor="tagNameInput">Tag Name</label>
                {showProjects.length > 0 &&
                <select className="form-control col-md-4"
                        value={selectedTagName}
                        onChange={e => setSelectedTagName(e.target.value)}>
                  {
                    showProjects.find(
                        p => p.projectName === selectedProjectName).tags.map(
                        tag => {
                          return <option key={tag.tagName}
                                         value={tag.tagName}>{tag.tagName}</option>
                        })

                  }
                </select>
                }
              </div>
            </form>
          </Modal.Body>
          <Modal.Footer>
            <button type="submit" className="btn btn-primary"
                    onClick={handleSaveDependency}>{determineButtonLabel().label}</button>
            <Button onClick={close}>Close</Button>
          </Modal.Footer>
        </Modal>
      </div>
  );

}
