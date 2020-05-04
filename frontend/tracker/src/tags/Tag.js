import React, {Component} from "react";
import history from '../history';
import {Button} from "react-bootstrap";
import {Route, Switch, Link} from "react-router-dom";
import {DisplayTags} from "./DisplayTags";
import {PropsPage} from "./propspage";

export class Tag extends Component {
    constructor(props) {
        super(props);

        this.state = {
            showTagOptions: false,
        }
    }


    /*  handleOptions = () => {
       if (this.state.showTagOptions) {
          this.setState({showTagOptions: false});

        } else {
          this.setState({showTagOptions: true});

        }

      }*/



    render() {



        return (
            <div>

    <Link className="btn btn-outline-success btn-lg btn-block mb-2" to={{
       pathname:'/projects/tags',
       aboutProps: {
           tags : this.props.tags,
           projectName: this.props.projectName
       }
    }}  >
         <span>
            <span className="fa fa-tag p-2"></span>
            <span className="badge badge-light">{this.props.tag.tagName}</span>
        </span>
        <br/>Tags
    </Link>

              {/*  <Button className="btn btn-outline-success btn-lg btn-block mb-2"
                        role="button" onClick={() => history.push('/tags')}>
        <span>
            <span className="fa fa-tag p-2"></span>
            <span className="badge badge-light">{this.props.tags[0].tagName}</span>
        </span>
                    <br/>Tags
                </Button>*/}
                {/*<Route path="/tags" render={(props) => <DisplayTags {...this.props.tags} {...props}/>}/>*/}


            </div>

        );
    }

    /* <div>
       <button className="btn btn-outline-success btn-lg btn-block mb-2"
               role="button" onClick={this.handleOptions}>
       <span>
           <span className="fa fa-tag p-2"></span>
           <span className="badge badge-light">v0.1.1</span>
       </span>
         <br/>Tags
       </button>
       {this.state.showTagOptions && <TagOptions/>}

     </div>*/

}
