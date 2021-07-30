export class PensionDetail {
    constructor(
        public name: String,
        public dateOfBirth: Date,
        public pan: String,
        public pensionType: String,
        public pensionAmount: number
    ) { }
}
