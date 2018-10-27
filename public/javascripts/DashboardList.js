const DashboardList = {

    data: function () {
        return {
            boards: [],
            board: {},
            events: []
        }
    },
    created() {
        this.events = [];
        const socket = new WebSocket("ws://localhost:9000"+prefix+"/boards/events");
        socket.onmessage = this.handleMessage;
        axios
            .get(prefix + '/boards')
            .then(r => {
                this.boards = r.data
            })
    },
    methods: {
        handleMessage(msg){
            console.log(msg);
            this.events.push(JSON.parse(msg.data))
        },
        createBoard() {
            axios
                .post(prefix + '/boards', this.board)
                .then(r => {
                    this.board = {};
                    console.log(r);
                    router.push({path: `/show/${r.data.id}`})
                })
        }
    },

    template: `
<div class="columns is-multiline">

<div class="column is-three-quarters">
            <ul>
              <li v-for="(b, index) in boards">
                 {{ index }} -  <router-link :to="'/show/' + b.id">{{b.title}}</router-link> : {{b.description}}
              </li>
            </ul>
</div>

<div class="column is-one-quarter">
            <h4>Events : </h4>
            <ul>
              <li v-for="(e, index) in events">
                 {{e}}
              </li>
            </ul>
</div>

<div class="column is-three-quarters">
        <form v-on:submit.prevent="createBoard">

            <div class="field">
                <label class="label" for="title">Title</label>
                <div class="control">
                    <input class="input" type="text" id="title" v-model="board.title">
                </div>
            </div>

            <div class="field">
                <label class="label" for="description">Description</label>
                <div class="control">
                    <textarea class="textarea" id="description" v-model="board.description"></textarea>
                </div>
            </div>
            <div class="field">
                <div class="control">
                    <button class="button is-link">Submit</button>
                </div>
            </div>
        </form>
</div>    
    </div>
   `
};

