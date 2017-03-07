import { DwNgAppPage } from './app.po';

describe('dw-ng-app App', function() {
  let page: DwNgAppPage;

  beforeEach(() => {
    page = new DwNgAppPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
