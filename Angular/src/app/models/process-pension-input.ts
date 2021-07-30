export class ProcessPensionInput {

    constructor(
        public aadhaarNumber: string,
        public pensionAmount: Number,
        public bankServiceCharge: Number,
    ) { }
}
