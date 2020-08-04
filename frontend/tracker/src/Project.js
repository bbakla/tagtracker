import React, {useState} from "react";
import Tag from "./tags/Tag";
import Deployment from "./deployment/Deployment";
import {ShowDependency} from "./dependencies/ShowDependency";

export default function Project({project, removeProject}) {

    const [selectedTagIndex, setSelectedTagIndex] = useState(0);

    const handleSelect = (event) => {
        let selectedTagName = event.target.value

        let index = project.tags.findIndex(t => (t.tagName === selectedTagName));

        setSelectedTagIndex(index);
    }

    const handleRemove = () => {
        let confirmDelete = window.confirm("Are you sure deleting the tag?")
        if (confirmDelete) {
            removeProject(project.projectName)
        }
    }

    const saveTag = (tag = {}) => {
        console.log("in project");
        //console.log(tag);
    }

    const description = project.description.length === 0 ? <br/>
        : project.description
    const tagName = project.tags.length === 0 ? ""
        : project.tags[selectedTagIndex].tagName
    const tagList = project.tags.length === 0 ? [] : project.tags
    const deployments = project.tags.length === 0 ? []
        : project.tags[selectedTagIndex].deployments
    const dependentToMe = project.tags.length === 0 ? []
        : project.tags[selectedTagIndex].dependentToMe
    const dependentOn = project.tags.length === 0 ? []
        : project.tags[selectedTagIndex].dependentOn

    return (
        <div key={project.name} className="col-md-6">

            <div className="card p-1">
                <h5 className=" card-header">{project.name}
                    <button className="outline-danger float-right btn-sm"
                            onClick={handleRemove}><i
                        className=" far fa-trash-alt"></i></button>
                </h5>

                <div className="card-body">

                    <select className="form-control col-md-4"
                            onChange={handleSelect}>

                        {
                            project.tags.map(tag => (
                                <option key={tag.tagName} value={tag.tagName}>{tag.tagName} </option>
                            ))
                        }
                    </select>

                    <div>
                        <p className="card-title font-weight-bold">{description}</p>

                    </div>

                    <div className="row">
                        <div className="col-xs-6 col-md-6">

                            <Tag currentTagName={tagName}
                                 projectName={project.name}
                                 projectId={project.projectId}
                                 tags={tagList}
                            />

                              <Deployment deploymentStatus={deployments}
                                        projectId={project.projectId}/>
                        </div>
                        <div className="col-xs-6 col-md-6">
                            <ShowDependency relationshipType="dependentToMe"
                                            dependencies={dependentToMe}
                                            projectName={project.name}
                                            projectId={project.projectId}
                            />
                            <ShowDependency relationshipType="dependentOn"
                                            dependencies={dependentOn}
                                            projectName={project.name}
                                            projectId={project.projectId}
                            />
                        </div>
                    </div>
                </div>
                {/*   <ButtonGroup className="p-3" toggle={true}>
                        <Button variant="outline-info"> <i className="fas fa-edit"></i></Button>
                        <Button variant="outline-warning"><i className="fa fa-plus"></i></Button>
                        <Button variant="outline-danger"> <i className="far fa-trash-alt"></i></Button>

                    </ButtonGroup>*/}
            </div>

        </div>
    )

}
