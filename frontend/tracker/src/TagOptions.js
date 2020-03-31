import React, {Component} from "react";
import {NewTagForm} from "./NewTagForm";

export class TagOptions extends Component {
  render() {
    return (
        <div className="btn-group-sm pb-4ö">
          <button className="btn btn-outline-blue-grey btn-block">Edit Tag
          </button>
          <button className="btn btn-outline-danger btn-block">Delete Tag
          </button>
          <NewTagForm/>
        </div>
    );
  }
}
