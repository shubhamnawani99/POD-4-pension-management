import { ProcessPensionInput } from './process-pension-input';

describe('ProcessPensionInput', () => {
  it('should create an instance', () => {
    expect(new ProcessPensionInput("", 0, 0)).toBeTruthy();
  });
});
