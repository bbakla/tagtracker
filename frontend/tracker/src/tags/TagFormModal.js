import React, {useState} from "react";
import Button from "react-bootstrap/Button"
import {Modal} from "react-bootstrap";

export default function TagFormModal({tName, m, releaseN, buttonLabel, saveTag}) {

    const [showModal, setShowModal] = useState(false);
    const [tagName, setTagName] = useState(tName);
    const [message, setMessage] = useState(m);
    const [releaseNotes, setReleaseNotes] = useState(releaseN);


    const close = () => {
        setShowModal(false);
    }

    const open = () => {
        setShowModal(true);
    }

    const saveTagHandle = () => {
      saveTag({
        name: tagName,
        message: message,
        releaseNotes: releaseNotes
      });

      setTagName("");
      setMessage("");
      setReleaseNotes("");
      close();
    }

/*    const saveValue = (event) => {
        this.setState({[event.target.name]: event.target.value});
    }*/


        const label = buttonLabel;
        let title = "";
        let button = '';
        let disabled = false;

        if (label === "Edit") {
            title = "Edit tag";
            disabled = true;
            button = <button className="float-left btn btn-sm btn-success" onClick={open}>
                <i className="fas fa-edit"></i>
            </button>
        } else {
            button = <Button className="btn btn-outline-primary btn-block" onClick={open}>
                Create New Tag
            </Button>
            title = "Create New Tag";

        }

        return (
            <div>
                {button}
                <Modal show={showModal} onHide={close}>
                    <Modal.Header>{title}</Modal.Header>
                    <Modal.Body>

                        <form>
                            <div className="form-group">
                                <label htmlFor="tagNameInput">Tag Name </label>
                                <input type="text" className="form-control" name="tagName" aria-describedby="tagName"
                                       disabled={disabled} onChange={e => setTagName(e.target.value)}
                                       placeholder="Enter tag name with the format v2.5.6"
                                       defaultValue={tagName}/>
                                <small id="emailHelp" className="form-text text-muted">should be in the format of
                                    v0.2.1 </small>
                            </div>
                            <div className="form-group">
                                <label htmlFor="messageInput">Message</label>
                                <input type="text" className="form-control" name="message" onChange={e => setMessage(e.target.value)}
                                       placeholder="Enter message" disabled={disabled}
                                       defaultValue={message}/>
                            </div>

                            <div className="form-group">
                                <label htmlFor="releaseNotesInput">Release note</label>
                                <input type="text" className="form-control" name="releaseNotes"
                                       onChange={e => setReleaseNotes(e.target.value)} placeholder="Enter release notes"
                                       defaultValue={releaseNotes}/>
                            </div>
                        </form>

                    </Modal.Body>
                    <Modal.Footer>
                        <button type="submit" className="btn btn-primary" onClick={saveTagHandle}>{label}</button>
                        <Button onClick={close}>Close</Button>
                    </Modal.Footer>
                </Modal>
            </div>
        );


}
