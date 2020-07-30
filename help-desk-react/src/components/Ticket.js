import React, { Component } from "react";
import axios from "axios";
import { Link } from "react-router-dom";
import HistoryTable from "./HistoryTable";
import CommentsTable from "./CommentsTable";

class Ticket extends Component {
  constructor(props) {
    super(props);
    this.state = {
      id: this.props.match.params.id,
      user: this.props.user,
      ticket: "",
      table: "history",
      comment: "",
      tableKey: 1,
      errorMessage: ""
    };
  }

  componentDidMount() {
    this.getTicket();
  }
  changeToHistoryTable = () => {
    this.setState({ table: "history" });
  };
  changeToCommentsTable = () => {
    this.setState({ table: "comments" });
  };
  getTicket() {
    const userId = this.state.user.id;
    const id = this.state.id;
    const authorization = this.props.getAuthorization();

    axios
      .get(
        "http://localhost:8080/help-desk/api/users/" +
          userId +
          "/tickets/" +
          id,
        {
          headers: { authorization: authorization }
        }
      )
      .then(response => {
        this.setState({ ticket: response.data }, () => {
          console.log(this.state);
        });
      })
      .catch(() => {
        this.props.history.push("/not-found");
      });
  }
  downloadAttachment = () => {
    const userId = this.state.user.id;
    const ticketId = this.state.id;
    const id = this.state.ticket.attachmentId;
    const file = this.state.ticket.attachmentName;
    const authorization = this.props.getAuthorization();
    axios
      .get(
        "http://localhost:8080/help-desk/api/users/" +
          userId +
          "/tickets/" +
          ticketId +
          "/attachments/" +
          id,
        {
          responseType: "arraybuffer",
          headers: {
            authorization: authorization
          }
        }
      )
      .then(response => {
        const url = window.URL.createObjectURL(new Blob([response.data]));
        const link = document.createElement("a");
        link.href = url;
        link.setAttribute("download", file);
        document.body.appendChild(link);
        link.click();
      })
      .catch(error => console.log(error));
  };
  submitComment = () => {
    const userId = this.state.user.id;
    const ticketId = this.state.id;
    const authorization = this.props.getAuthorization();

    axios
      .post(
        "http://localhost:8080/help-desk/api/users/" +
          userId +
          "/tickets/" +
          ticketId +
          "/comments",
        {
          userId: userId,
          text: this.state.comment,
          ticketId: ticketId
        },
        {
          headers: { authorization: authorization }
        }
      )
      .then(() => {
        this.setState(
          { tableKey: Math.random(), comment: "", errorMessage: "" },
          () => {
            console.log(this.state);
          }
        );
      })
      .catch(error => {
        let errorStr = "Error: " + error.response.data.errors[0];

        this.setState({
          errorMessage: errorStr
        });
      });
  };

  handleChangeComment = e => {
    this.setState({
      comment: e.target.value
    });
  };
  handleBack = () => {
    this.props.history.push("/help-desk");
  };
  handleFeedbackLeave = () => {
    const id = this.state.id;
    this.props.history.push("/help-desk/ticket/" + id + "/feedback/leave");
  };
  handleFeedbackView = () => {
    const id = this.state.id;
    this.props.history.push("/help-desk/ticket/" + id + "/feedback/view");
  };
  render() {
    const FeedbackButton = () => {
      const state = this.state.ticket.stateId;
      const ownerId = this.state.ticket.ownerId;
      if (state === 6 && ownerId === this.state.user.id) {
        if (this.state.ticket.feedbackId != null) {
          return (
            <button
              type="button"
              className="btn btn-success btn-sm"
              onClick={this.handleFeedbackView}
            >
              View feedback
            </button>
          );
        } else
          return (
            <button
              type="button"
              className="btn btn-success btn-sm"
              onClick={this.handleFeedbackLeave}
            >
              Leave feedback
            </button>
          );
      }
      return <></>;
    };
    const toTicketEdition = {
      pathname: "/help-desk/ticket/" + this.state.id + "/edit"
    };
    return (
      <div className="container">
        <div className="row">
          <div className="col-10 mx-auto col-md-8 mt-4">
            <button
              type="button"
              className="btn btn-success"
              onClick={this.handleBack}
            >
              Ticket List
            </button>
            <div className="d-flex justify-content-between my-2">
              <h1>Ticket: {this.state.ticket.name}</h1>
              <div className="d-flex align-items-end flex-column bd-highlight mb-3">
                {this.state.user.id === this.state.ticket.ownerId &&
                (this.state.ticket.state.toLowerCase() === "draft" ||
                  this.state.ticket.state.toLowerCase() === "declined") ? (
                  <>
                    <Link to={toTicketEdition}>
                      <button type="button" className="btn btn-success ">
                        Edit ticket
                      </button>
                    </Link>
                    &nbsp;
                  </>
                ) : (
                  <></>
                )}
                <FeedbackButton />
              </div>
            </div>
            <h6>Created on: {this.state.ticket.creationDate}</h6>
            <h6>State : {this.state.ticket.state}</h6>
            <h6>Category : {this.state.ticket.category}</h6>
            <h6>Urgency : {this.state.ticket.urgency}</h6>
            <h6>
              Desired resolution date :{" "}
              {this.state.ticket.desiredResolutionDate}
            </h6>
            <h6>Owner : {this.state.ticket.owner}</h6>
            <h6>Approver : {this.state.ticket.approver}</h6>
            <h6>Assignee : {this.state.ticket.assignee}</h6>
            <h6>Description : {this.state.ticket.description}</h6>

            {this.state.ticket.attachmentId !== null ? (
              <>
                <div className="d-flex justify-content-start">
                  <h6>Attachment : {this.state.ticket.attachmentName}</h6>
                  &nbsp;
                  <button
                    className="btn btn-warning btn-sm"
                    onClick={this.downloadAttachment}
                  >
                    Download
                  </button>
                </div>
                &nbsp;
              </>
            ) : (
              <></>
            )}
            <div className="form-group row">
              <h6 className="text-danger" key={1 + Math.random()}>
                {this.state.errorMessage}
              </h6>
              <div class="col-sm-10">
                <textarea
                  class="form-control"
                  id="commentTextarea"
                  rows="3"
                  onChange={this.handleChangeComment}
                  value={this.state.comment}
                ></textarea>
              </div>
            </div>
            <button
              type="button"
              className="btn btn-warning btn-sm"
              onClick={this.submitComment}
            >
              Leave comment
            </button>
            <div className="d-flex justify-content-between my-2">
              <button
                className="btn btn-block btn-primary mt-3"
                onClick={this.changeToHistoryTable}
              >
                History
              </button>
              &nbsp;
              <button
                className="btn btn-block btn-outline-info mt-3"
                onClick={this.changeToCommentsTable}
              >
                Comments
              </button>
            </div>
            {this.state.table === "history" ? (
              <HistoryTable
                userId={this.state.user.id}
                ticketId={this.state.id}
                key={this.state.tableKey}
                getAuthorization={this.props.getAuthorization}
              />
            ) : (
              <CommentsTable
                userId={this.state.user.id}
                ticketId={this.state.id}
                key={this.state.tableKey}
                getAuthorization={this.props.getAuthorization}
              />
            )}
          </div>
        </div>
      </div>
    );
  }
}
export default Ticket;
