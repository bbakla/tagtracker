import React, {Component, useContext, useState} from "react";
import {Modal} from "react-bootstrap";
import Button from "react-bootstrap/Button";

export default function DependencyModal({pName, tName, saveDependency, buttonLabel, projects}){

const [showModal, setShowModal] = useState(false);
const [tagName, setTagName] = useState(tName);
const [showProjects, setShowProjects] = useState(projects.filter(p => p.projectName !== pName));
const [selectedProjectName, setSelectedProjectName] = useState(showProjects.length === 0 ? "0" : showProjects[0].projectName);


    const open = () => {
        setShowModal(true);
    }

    const close = () => {
        setShowModal(false);
    }

    const handleSaveDependency = () => {

        saveDependency({
            projectName: selectedProjectName,
            tagName: tagName
        });

        close();
    }
        let label = buttonLabel;
        let button = '';

        if (label === "Edit") {
            label = "Save"
            button = <button className="float-left btn btn-sm btn-success" onClick={open}>
                <i className="fas fa-edit"></i>
            </button>
        } else {
            label = "Create"
            button =
                <Button className="btn btn-outline-primary btn-block" onClick={open}>Create dependency</Button>
        }

        return (
            <div>
                {button}
                <Modal show={showModal} onHide={close}>
                    <Modal.Header>Dependency</Modal.Header>
                    <Modal.Body>
                        <form>
                            <div className="form-group">
                                <label htmlFor="projectNameInput">Project Name</label>
                                <select className="form-control col-md-4"
                                        onChange={e => setSelectedProjectName(e.target.value)} value={selectedProjectName}>
                                    {
                                        showProjects.map(p => {
                                               return (<option key={p.projectName} value={p.projectName}>{p.projectName} </option>)
                                    })
                                    }
                                </select>

                            </div>

                            <div className="form-group">
                                <label htmlFor="tagNameInput">Tag Name</label>
                                {showProjects.length > 0 && <select className="form-control col-md-4"
                                        onChange={e => setTagName(e.target.value)}>
                                    {
                                        showProjects.find(p => p.projectName === selectedProjectName).tags.map(tag => {
                                        return <option key={tag.tagName} value={tag.tagName}>{tag.tagName}</option>
                                    })}
                                </select>}
                            </div>
                        </form>
                    </Modal.Body>
                    <Modal.Footer>
                        <button type="submit" className="btn btn-primary" onClick={handleSaveDependency}>{label}</button>
                        <Button onClick={close}>Close</Button>
                    </Modal.Footer>
                </Modal>
            </div>
        );

}
