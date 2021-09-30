import React from "react";

class UserSignupPage extends React.Component {
    render() {
        return (
            <div>
                <h1>Sign Up</h1>
                <div>
                    <input placeholder="Your display name"/>
                </div>
                <div>
                    <input placeholder="Your username"/>
                </div>
                <div>
                    <input type="password" placeholder="Your password"/>
                </div>
                <div>
                    <input type="password" placeholder="Repeat your password"/>
                </div>
                <div>
                    <button>Sign Up</button>
                </div>
            </div>
        )
    }
}

export default UserSignupPage;
