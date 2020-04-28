import React, {Component} from "react";

import {TagFormModal} from "./TagFormModal";
import {Table} from "react-bootstrap";

export class DisplayTags extends Component{
    constructor(props) {
        super(props);

        this.state = {
            showModal: false,
            tags: [
                {
                    tagName: 'v0.1.0',
                    message: "message1",
                    releaseNotes: "releaseNote1"
            },
                {
                    tagName: 'v0.1.4',
                    message: "message2",
                    releaseNotes: "releaseNote2"
                },
                {
                    tagName: 'v0.2.0',
                    message: "message3",
                    releaseNotes: "releaseNote3"
                },
            ],
        }
    }

    editTagForm = () => {
        console.log("displayTags");
        this.setState({showModal: true})
    }

    deleteTag = (tagName) => {
        //console.log("delete tag");
        let confirmDelete = window.confirm("Are you sure deleting the tag?")
        if(confirmDelete) {
            this.setState({
                tags: this.state.tags.filter(tag => tag.tagName !== tagName)
            });
        }
    }

    render() {

        const tagsList = this.state.tags.map( tag => {
            return(
            <tr key={tag.tagName}>
                <td>{tag.message}</td>
                <td>{tag.releaseNotes}</td>

                <td>
                    <div>
                   {/* <button type="button" className="btn btn-success" onClick={this.editTagForm}>
                        <i className="fas fa-edit"></i>
                    </button>*/}
                    <TagFormModal buttonLabel="Edit" />
                        {' '}



                    <button type="button" className="btn btn-danger" onClick={ () => this.deleteTag(tag.tagName)}>
                        <i className="far fa-trash-alt"></i>
                    </button>
                    </div>
                </td>
            </tr>

            )});

        return (

         /*   <div className="container">
                <div className="row">
                    <div className="col-12">
                <table className="table table-striped table-responsive-md btn-table">
                    <thead>
                    <tr>
                        <th>Tag Name</th>
                        <th>Message</th>
                        <th>Release Notes</th>
                    </tr>
                    </thead>

                    <tbody>
                        {tagsList}
                    </tbody>
                </table>

                <NewTagForm/>
            </div>
                </div>
            </div>*/

            <div className="container">
                <div className="row">
                    <div className="col-12">
         <Table  responsive hover>
             <thead>
             <tr>
                 <th>Tag Name</th>
                 <th>Message</th>
                 <th>Release Notes</th>
             </tr>
             </thead>

             <tbody>
             {tagsList}
             </tbody>
         </Table>

             <TagFormModal buttonLabel="Create" />
         </div>
                </div>
            </div>

        );
    }
}
