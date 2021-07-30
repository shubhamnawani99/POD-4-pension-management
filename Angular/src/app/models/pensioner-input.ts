export class PensionerInput {
    constructor(
        public name: String,
        public dateOfBirth: Date,
        public pan: String,
        public aadhaarNumber: String,
        public pensionType: String,
    ) { }
}
