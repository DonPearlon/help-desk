import React, { Component } from "react";
import axios from "axios";
import { Link } from "react-router-dom";

class EditTicket extends Component {
  constructor(props) {
    super(props);
    this.state = {
      id: this.props.match.params.id,
      user: this.props.user,
      ticket: "",
      categoryId: "",
      name: "",
      description: "",
      urgencyId: "",
      desiredResolutionDate: "",
      attachmentId: null,
      attachment: "",
      comment: "",
      categories: [],
      currentDate: new Date().toISOString().slice(0, 10),
      errorMessages: [],
      defaultSelectOption: null,
      categoryKey: 1
    };
  }
  componentDidMount() {
    this.getTicket();
    this.getCategories();
  }

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
        this.setState(
          {
            ticket: response.data,
            categoryId: response.data.categoryId,
            urgencyId: response.data.urgencyId,
            name: response.data.name,
            description: response.data.description,
            desiredResolutionDate: response.data.desiredDateStr,
            attachmentId: response.data.attachmentId
          },
          () => {
            console.log(this.state);
          }
        );
      })
      .catch(() => {
        this.props.history.push("/not-found");
      });
  }

  removeAttachment = () => {
    const userId = this.state.user.id;
    const id = this.state.id;
    const authorization = this.props.getAuthorization();
    axios
      .delete(
        "http://localhost:8080/help-desk/api/users/" +
          userId +
          "/tickets/" +
          id +
          "/attachments",
        {
          headers: { authorization: authorization }
        }
      )
      .then(() => {
        this.setState({ attachmentId: null });
      })
      .catch(error => console.log(error));
  };
  clearInputFile = () => {
    this.setState({ attachment: "" });
    this.fileInput.value = "";
  };
  getCategories() {
    const authorization = this.props.getAuthorization();
    axios
      .get("http://localhost:8080/help-desk/api/categories", {
        headers: { authorization: authorization }
      })
      .then(response => {
        this.setState({ categories: response.data }, () => {
          console.log(this.state);
        });
      });
  }
  updateTicket = ticketState => {
    const file = this.state.attachment;
    const formData = new FormData();
    const authorization = this.props.getAuthorization();
    console.log("Description: " + this.state.description);
    formData.append(
      "ticket",
      new Blob(
        [
          JSON.stringify({
            ownerId: this.state.user.id,
            stateId: ticketState,
            categoryId: this.state.categoryId,
            name: this.nameInput.value,
            description: this.descriptionInput.value,
            urgencyId: this.state.urgencyId,
            desiredResolutionDate: this.state.desiredResolutionDate,
            comment: this.state.comment
          })
        ],
        {
          type: "application/json"
        }
      )
    );
    formData.append("file", file);
    const userId = this.state.user.id;
    const ticketId = this.state.id;
    axios({
      url:
        "http://localhost:8080/help-desk/api/users/" +
        userId +
        "/tickets/" +
        ticketId,

      method: "POST",
      headers: { authorization: authorization },

      data: formData
    })
      .then(() => {
        let arr = [];
        this.setState({ errorMessages: arr }, () => {
          this.props.history.goBack();
        });
      })
      .catch(error => {
        let errors = error.response.data.errors;

        this.setState({
          errorMessages: errors
        });
      });
  };

  updateTicketNewState = () => {
    let ticketState = 2;
    this.updateTicket(ticketState);
  };

  updateTicketDraftState = () => {
    let ticketState = 1;
    this.updateTicket(ticketState);
  };

  handleChangeCategoryId = e => {
    this.setState({
      categoryId: e.target.value,
      categoryKey: Math.random()
    });
  };
  handleChangeName = e => {
    let nameNew = e.target.value;
    this.setState({
      name: nameNew
    });
  };
  handleChangeDescription = e => {
    this.setState({
      description: e.target.value
    });
  };
  handleChangeUrgencyId = e => {
    this.setState({
      urgencyId: e.target.value
    });
  };
  handleChangeDesiredDate = e => {
    let inputDate = e.target.value;
    let currentDate = new Date().toISOString().slice(0, 10);
    if (inputDate >= currentDate) {
      this.setState({
        desiredResolutionDate: e.target.value
      });
    } else {
      this.dateInput.value = null;
      this.setState({
        desiredResolutionDate: ""
      });
    }
  };
  handleDateInputKeyPress = e => {
    e.preventDefault();
  };
  handleChangeAttachment = e => {
    let file = e.target.files[0];
    this.setState({
      attachment: file
    });
  };
  handleChangeComment = e => {
    this.setState({
      comment: e.target.value
    });
  };
  render() {
    const CategoryOptions = () => (
      <>
        <select
          className="custom-select mr-sm-2"
          id="ticketCategory"
          onChange={this.handleChangeCategoryId}
          defaultValue={this.state.categoryId}
        >
          {this.state.categories.map(category => (
            <option value={category.id}>{category.name}</option>
          ))}
        </select>
      </>
    );

    const ErrorMessagesOutput = () => (
      <>
        {this.state.errorMessages.map(message => (
          <h6 className="text-danger" key={1 + Math.random()}>
            Error: {message}
          </h6>
        ))}
      </>
    );
    const toTicketOverview = {
      pathname: "/help-desk/ticket/" + this.state.id
    };
    return (
      <div className="container">
        <div className="row">
          <div className="col-10 mx-auto col-md-8 mt-4">
            <h1 className="text-capitalize text-center">Edit Ticket:</h1>
            <ErrorMessagesOutput />
            <Link to={toTicketOverview}>
              <button type="button" className="btn btn-success btn-sm">
                Ticket Overview
              </button>
            </Link>
            &nbsp;
            <form>
              <div className="form-group row">
                <h5 className="col-sm-2 col-form-label-lg">
                  Category<span className="text-danger">*</span>
                </h5>
                <div class="col-5">
                  &nbsp;
                  <CategoryOptions key={this.state.categoryKey} />
                </div>
              </div>
              <div className="form-group row">
                <h5 className="col-sm-2 col-form-label-lg">
                  Name<span className="text-danger">*</span>
                </h5>
                <div class="col-5">
                  <input
                    required
                    type="text"
                    className="form-control"
                    defaultValue={this.state.ticket.name}
                    onChange={this.handleChangeName}
                    ref={ref => (this.nameInput = ref)}
                  ></input>
                </div>
              </div>
              <div className="form-group row">
                <h5 className="col-sm-2 col-form-label-lg">Description</h5>
                <div class="col-sm-10">
                  <textarea
                    class="form-control"
                    id="descriptionTextarea"
                    rows="5"
                    defaultValue={this.state.ticket.description}
                    onChange={this.handleChangeDescription}
                    ref={ref => (this.descriptionInput = ref)}
                  ></textarea>
                </div>
              </div>
              <div className="form-group row">
                <h5 className="col-sm-2 col-form-label-lg">
                  Urgency<span className="text-danger">*</span>
                </h5>
                <div class="col-5">
                  <select
                    class="custom-select mr-sm-2"
                    id="ticketUrgency"
                    onChange={this.handleChangeUrgencyId}
                    value={this.state.urgencyId}
                  >
                    <option value="1">Critical</option>
                    <option value="2">High</option>
                    <option value="3">Average</option>
                    <option value="4">Low</option>
                  </select>
                </div>
              </div>
              <div className="form-group row">
                <label className="col-2 col-form-label">Desired date</label>
                <div className="col-5">
                  <input
                    className="form-control"
                    type="date"
                    id="desired-date-input"
                    defaultValue={this.state.ticket.desiredDateStr}
                    min={this.state.currentDate}
                    onChange={this.handleChangeDesiredDate}
                    onKeyPress={this.handleDateInputKeyPress}
                    ref={ref => (this.dateInput = ref)}
                  />
                </div>
              </div>
              {this.state.attachmentId == null ? (
                <>
                  <div className="form-group row">
                    <label className="col-2 col-form-label">
                      Add attachments
                    </label>
                    <div className="col-sm-10">
                      <input
                        type="file"
                        className="form-control-file"
                        id="fileInput"
                        onChange={this.handleChangeAttachment}
                        ref={ref => (this.fileInput = ref)}
                      ></input>
                      {this.state.attachment !== "" ? (
                        <>
                          <button
                            type="button"
                            className="btn btn-warning btn-sm"
                            onClick={this.clearInputFile}
                          >
                            Remove
                          </button>
                        </>
                      ) : (
                        <></>
                      )}
                    </div>
                  </div>
                </>
              ) : (
                <div className="d-flex justify-content-start">
                  <h6>Attachment : {this.state.ticket.attachmentName}</h6>
                  <button
                    type="button"
                    className="btn btn-warning btn-sm"
                    onClick={this.removeAttachment}
                  >
                    Remove
                  </button>
                </div>
              )}
              &nbsp;
              <div className="form-group row">
                <h5 className="col-sm-2 col-form-label-lg">Comment</h5>
                <div className="col-sm-10">
                  <textarea
                    className="form-control"
                    id="commentTextarea"
                    rows="3"
                    onChange={this.handleChangeComment}
                  ></textarea>
                </div>
              </div>
            </form>
            <div className="d-flex justify-content-end m-2">
              <button
                type="button"
                className="btn btn-outline-success btn-sm"
                onClick={this.updateTicketDraftState}
              >
                Save as Draft
              </button>
              &nbsp;&nbsp;&nbsp;
              <button
                type="button"
                className="btn btn-success btn-sm"
                onClick={this.updateTicketNewState}
              >
                Submit
              </button>
            </div>
          </div>
        </div>
      </div>
    );
  }
}
export default EditTicket;
