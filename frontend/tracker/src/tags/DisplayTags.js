import React, {Component} from "react";
import {NewTagForm} from "./NewTagForm";
import {EditTag} from "./EditTag";
import {Tag} from "./Tag";
import {TagForm} from "./TagForm";
import {TagFormModal} from "./TagFormModal";

export class DisplayTags extends Component{
    constructor(props) {
        super(props);

        this.state = {
            showModal: false,
        }
    }

    editTagForm = () => {
        this.setState({showModal: true})
    }

    render() {
        return (

            <div className="container">
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
                    <tr>
                        <td>v0.1.0</td>
                        <td>message1</td>
                        <td>releaseNotes1</td>
                        <td>
                            <button type="button" className="btn btn-success" onClick={this.editTagForm}>
                                <i className="fas fa-edit"></i>
                            </button>
                            {this.state.showModal && <TagFormModal/>}


                            <button type="button" className="btn btn-warning ">
                                <i className="far fa-trash-alt"></i>
                            </button>
                        </td>
                    </tr>
                    <tr>
                        <td>v0.1.2</td>
                        <td>message2</td>
                        <td>releaseNotes2</td>
                        <td>
                            <button type="button" className="btn btn-success">
                                <i className="fas fa-edit"></i>
                            </button>

                            <button type="button" className="btn btn-warning">
                                <i className="far fa-trash-alt"></i>
                            </button>
                        </td>
                    </tr>
                    <tr>
                        <td>v0.1.2</td>
                        <td>message2</td>
                        <td>releaseNotes2</td>
                        <td>
                            <button type="button" className="btn btn-success">
                                <i className="fas fa-edit"></i>
                            </button>

                            <button type="button" className="btn btn-warning">
                                <i className="far fa-trash-alt"></i>
                            </button>
                        </td>
                    </tr>
                    </tbody>
                </table>

                <NewTagForm/>
            </div>
                </div>
            </div>


        );
    }
}
