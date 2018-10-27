Array.prototype.diff = function(a) {
    return this.filter(function(i) {return a.indexOf(i) < 0;});
};
const prefix = '/api/v1/typed';
const DashboardShow = {

    data: function () {
        return {
            board: {},
            ticket: {},
            tickets : [],
            events : []
        }
    },
    methods: {
        handleMessage(msg){
            console.log(msg);
            this.events.push(msg.data)
        },
        createTicket() {
            axios
                .post(prefix + '/boards/'+this.board.id+'/tickets', this.ticket)
                .then(r => {
                    this.ticket = {};
                    this.tickets.push(r.data);
                })
        },
    },
    computed : {
        ticketsTodo: {
            get() {
                return this.tickets.filter(function (ticket) {
                    return ticket.status.type.toLowerCase() === 'todo'
                })
            },
            set(newArray) {
                const boardId = this.$route.params.id;
                let receivedElements = newArray.diff(this.ticketsTodo);
                receivedElements.forEach(function (t) {
                    axios
                        .put(prefix + '/boards/' + boardId + "/tickets/" + t.id + "/todo")
                        .then(t.status.type = "todo");
                });
            }
        },
        ticketsInProgress: {
            get() {
                return this.tickets.filter(function (ticket) {
                    return ticket.status.type.toLowerCase() === 'inprogress'
                })
            },
            set(newArray) {
                const boardId = this.$route.params.id;
                let receivedElements = newArray.diff(this.ticketsInProgress);
                receivedElements.forEach(function (t) {
                    axios
                        .put(prefix + '/boards/' + boardId + "/tickets/" + t.id + "/inprogress")
                        .then(t.status.type = "inprogress");
                });
            }
        },
        ticketsDone: {
            get() {
                return this.tickets.filter(function (ticket) {
                    return ticket.status.type.toLowerCase() === 'done'
                })
            },
            set(newArray) {
                const boardId = this.$route.params.id;
                let receivedElements = newArray.diff(this.ticketsDone);
                receivedElements.forEach(function (t) {
                    axios
                        .put(prefix + '/boards/' + boardId + "/tickets/" + t.id + "/done")
                        .then(t.status.type = "done");
                });
            }
        },
    },
    created: function () {

        const socket = new WebSocket("ws://localhost:9000"+prefix+"/boards/"+this.$route.params.id+"/events");
        socket.onmessage = this.handleMessage;

       axios
            .get(prefix + '/boards/'+this.$route.params.id)
            .then(r => {
                this.board = r.data
            });

        axios
            .get(prefix + '/boards/'+this.$route.params.id+"/tickets")
            .then(r => {
                this.tickets = r.data
            });


    },
    template: `
        <div class="column content">
            <div class="container">
                <h1>{{board.title}}</h1>
                <p><router-link to="/">index</router-link> > {{board.description}}</p>
                
            
            
          <div class="tile is-ancestor">
            <div class="tile is-parent">
                <article class="tile is-child box notification is-danger">
                  <p class="title">TODO</p>
                </article>
            </div>
            <div class="tile is-parent">
                <article class="tile is-child box notification is-info">
                  <p class="title">In progress</p>
                </article>
            </div>
            <div class="tile is-parent">
                <article class="tile is-child box notification is-success">
                  <p class="title">Done</p>
                </article>
            </div>
           </div>
           
           <div class="tile is-ancestor">
            <div class="tile is-parent is-vertical ">
                <draggable v-model='ticketsTodo'  style="min-height: 100px"   :options="{group:'tickets'}">
                    <div v-for="t in ticketsTodo" class="tile is-child box item" :key="t.id" >
                        <p class="title">{{t.title}}</p> 
                        <p class="subtitle">{{t.description}}</p> 
                    </div>
                </draggable>
            </div>
            
            <div class="tile is-parent is-vertical  dragArea">
                <draggable  v-model="ticketsInProgress" style="min-height: 100px" :options="{group:'tickets'}">
                    <div v-for="t in ticketsInProgress"  class="tile is-child box item" :key="t.id" >
                        <p class="title">{{t.title}}</p> 
                        <p class="subtitle">{{t.description}}</p> 
                    </div>
                </draggable>
            </div>
            
            <div class="tile is-parent is-vertical dragArea">
                <draggable  v-model="ticketsDone"  style="min-height: 100px" :options="{group:'tickets'}">
                    <div v-for="t in ticketsDone" :key="t.id" class="tile is-child box item" >
                        <p class="title">{{t.title}}</p> 
                        <p class="subtitle">{{t.description}}</p> 
                    </div>
                </draggable>
            </div>
           </div>
                   
            </div>
            
            <div class="container">
            <div class="columns">
                <div class="column is-half">
                    Create a ticket
                    <form v-on:submit.prevent="createTicket">
                        <div class="field">
                            <label class="label" for="title">Title</label>
                            <div class="control">
                                <input class="input" type="text" id="title" v-model="ticket.title">
                                <input type="hidden" id="status" v-model="ticket.status" value="Todo">
                            </div>
                        </div>
            
                        <div class="field">
                            <label class="label" for="description">Description</label>
                            <div class="control">
                                <textarea class="textarea" id="description" v-model="ticket.description"></textarea>
                            </div>
                        </div>
                        <div class="field">
                            <div class="control">
                                <button class="button is-link">Submit</button>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="column is-half">
                    <h4>Events : </h4>
                    <ul>
                        <li v-for="(e, index) in events.slice().reverse()">
                            {{e}}
                        </li>
                    </ul>
                </div>
            </div>
                
            </div>
        </div>
`};

