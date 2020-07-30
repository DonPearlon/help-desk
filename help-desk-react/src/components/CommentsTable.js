import React, { Component } from "react";
import axios from "axios";

class CommentsTable extends Component {
  constructor(props) {
    super(props);
    this.state = {
      comments: [],
      buttonAll: true
    };
  }
  componentDidMount() {
    this.getComments();
  }
  getComments() {
    const userId = this.props.userId;
    const ticketId = this.props.ticketId;
    const authorization = this.props.getAuthorization();
    axios
      .get(
        "http://localhost:8080/help-desk/api/users/" +
          userId +
          "/tickets/" +
          ticketId +
          "/comments",
        {
          headers: { authorization: authorization }
        }
      )
      .then(response => {
        this.setState({ comments: response.data }, () => {
          console.log(this.state);
        });
      });
  }

  getAllComments() {
    const userId = this.props.userId;
    const ticketId = this.props.ticketId;
    const authorization = this.props.getAuthorization();
    axios
      .get(
        "http://localhost:8080/help-desk/api/users/" +
          userId +
          "/tickets/" +
          ticketId +
          "/comments/all",
        {
          headers: { authorization: authorization }
        }
      )
      .then(response => {
        this.setState({ comments: response.data }, () => {
          console.log(this.state);
        });
      });
  }
  changeToAllComments = () => {
    this.setState({ buttonAll: false });
    this.getAllComments();
  };

  changeToTop5Comments = () => {
    this.setState({ buttonAll: true });
    this.getComments();
  };
  render() {
    const CommentRows = () => (
      <>
        {this.state.comments.map(comment => (
          <tr key={comment.id}>
            <td>{comment.date}</td>
            <td>{comment.userName}</td>
            <td>{comment.text}</td>
          </tr>
        ))}
      </>
    );
    return (
      <div className="container mb-3 mt-3">
        {this.state.buttonAll ? (
          <button
            type="button"
            className="btn btn-outline-info btn-sm"
            onClick={this.changeToAllComments}
          >
            Show All
          </button>
        ) : (
          <button
            type="button"
            className="btn btn-outline-info btn-sm"
            onClick={this.changeToTop5Comments}
          >
            Show Top 5
          </button>
        )}

        <table className="table table-striped table-bordered">
          <thead>
            <tr>
              <th>Date</th>
              <th>User</th>
              <th>Comment</th>
            </tr>
          </thead>
          <tbody>
            <CommentRows />
          </tbody>
        </table>
      </div>
    );
  }
}
export default CommentsTable;
