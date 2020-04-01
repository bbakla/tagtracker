import React, {Component} from "react";
import {NewTagForm} from "./NewTagForm";

export class DisplayTags extends Component{
    render() {
        return (

            <div>
                <NewTagForm/>
                <table className="table table-striped table-responsive-md btn-table">

                    <thead>
                    <tr>
                        <th>#</th>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>Username</th>
                    </tr>
                    </thead>

                    <tbody>
                    <tr>
                        <th scope="row">1</th>
                        <td>
                            <button type="button" className="btn btn-outline-primary btn-sm m-0 waves-effect">Button
                            </button>
                        </td>
                        <td>Otto</td>
                        <td>@mdo</td>
                    </tr>
                    <tr>
                        <th scope="row">2</th>
                        <td>Jacob</td>
                        <td>
                            <button type="button" className="btn btn-outline-primary btn-sm m-0 waves-effect">Button
                            </button>
                        </td>
                        <td>@fat</td>
                    </tr>
                    <tr>
                        <th scope="row">3</th>
                        <td>Larry</td>
                        <td>the Bird</td>
                        <td>
                            <button type="button" className="btn btn-outline-primary btn-sm m-0 waves-effect">Button
                            </button>
                        </td>
                    </tr>
                    </tbody>

                </table>


            </div>


        );
    }
}
