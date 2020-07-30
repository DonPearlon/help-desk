import React, { Component } from "react";
import axios from "axios";

class CreateTicket extends Component {
  constructor(props) {
    super(props);
    this.state = {
      id: this.props.user.id,
      categoryId: "",
      name: "",
      description: "",
      urgencyId: "",
      desiredResolutionDate: "",
      attachment: "",
      comment: "",
      categories: [],
      date: new Date().toISOString().slice(0, 10),
      errorMessages: [],
      defaultSelectOption: "",
      categoryKey: 1
    };
  }
  componentDidMount() {
    this.getCategories();
  }

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
  submitTicket = ticketState => {
    const file = this.state.attachment;
    const formData = new FormData();
    const authorization = this.props.getAuthorization();
    formData.append(
      "ticket",
      new Blob(
        [
          JSON.stringify({
            ownerId: this.state.id,
            stateId: ticketState,
            categoryId: this.state.categoryId,
            name: this.state.name,
            description: this.state.description,
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
    let id = this.state.id;
    axios({
      url: "http://localhost:8080/help-desk/api/users/" + id + "/tickets",

      method: "POST",
      headers: { authorization: authorization },

      data: formData
    })
      .then(() => {
        let arr = [];
        this.setState({ errorMessages: arr }, () => {
          this.props.history.push("/help-desk");
        });
      })
      .catch(error => {
        let errors = error.response.data.errors;
        if (errors !== undefined) {
          this.setState({
            errorMessages: errors
          });
        }
      });
  };

  clearInputFile = () => {
    this.setState({ attachment: "" });
    this.fileInput.value = "";
  };

  submitTicketNewState = () => {
    let ticketState = 2;
    this.submitTicket(ticketState);
  };

  submitTicketDraftState = () => {
    let ticketState = 1;
    this.submitTicket(ticketState);
  };

  handleBack = () => {
    this.props.history.push("/help-desk");
  };
  handleChangeCategoryId = e => {
    this.setState({
      categoryId: e.target.value,
      categoryKey: Math.random()
    });
  };
  handleChangeName = e => {
    this.setState({
      name: e.target.value
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
    const CategorySelect = () => (
      <>
        <select
          className="custom-select mr-sm-2"
          id="ticketCategory"
          onChange={this.handleChangeCategoryId}
          value={this.state.categoryId}
        >
          <option value={this.state.defaultSelectOption}>Choose...</option>
          {this.state.categories.map((category, key) => (
            <option key={key} value={category.id}>
              {category.name}
            </option>
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

    return (
      <div className="container">
        <div className="row">
          <div className="col-10 mx-auto col-md-8 mt-4">
            <h1 className="text-capitalize text-center">Create Ticket:</h1>
            <ErrorMessagesOutput />
            <button
              type="button"
              className="btn btn-success btn-sm"
              onClick={this.handleBack}
            >
              Ticket List
            </button>
            <form>
              <div className="form-group row">
                <h5 className="col-sm-2 col-form-label-lg">
                  Category<span className="text-danger">*</span>
                </h5>
                <div className="col-5">
                  <CategorySelect key={this.state.categoryKey} />
                </div>
              </div>
              <div className="form-group row">
                <h5 className="col-sm-2 col-form-label-lg">
                  Name<span className="text-danger">*</span>
                </h5>
                <div className="col-5">
                  <input
                    required
                    type="text"
                    className="form-control"
                    onChange={this.handleChangeName}
                  ></input>
                </div>
              </div>
              <div className="form-group row">
                <h5 className="col-sm-2 col-form-label-lg">Description</h5>
                <div className="col-sm-10">
                  <textarea
                    className="form-control"
                    id="descriptionTextarea"
                    rows="5"
                    onChange={this.handleChangeDescription}
                  ></textarea>
                </div>
              </div>
              <div className="form-group row">
                <h5 className="col-sm-2 col-form-label-lg">
                  Urgency<span className="text-danger">*</span>
                </h5>
                <div className="col-5">
                  <select
                    className="custom-select mr-sm-2"
                    id="ticketUrgency"
                    onChange={this.handleChangeUrgencyId}
                  >
                    <option value={this.state.defaultSelectOption}>
                      Choose...
                    </option>
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
                    min={this.state.date}
                    onChange={this.handleChangeDesiredDate}
                    onKeyPress={this.handleDateInputKeyPress}
                    ref={ref => (this.dateInput = ref)}
                  />
                </div>
              </div>
              <div className="form-group row">
                <label className="col-2 col-form-label">Add attachments</label>
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
                onClick={this.submitTicketDraftState}
              >
                Save as Draft
              </button>
              &nbsp;&nbsp;&nbsp;
              <button
                type="button"
                className="btn btn-success btn-sm"
                onClick={this.submitTicketNewState}
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
export default CreateTicket;
