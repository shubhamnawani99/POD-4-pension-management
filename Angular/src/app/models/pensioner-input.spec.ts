import { PensionerInput } from './pensioner-input';

describe('PensionerInput', () => {
  it('should create an instance', () => {
    expect(new PensionerInput("", new Date, "", "", "")).toBeTruthy();
  });
});
